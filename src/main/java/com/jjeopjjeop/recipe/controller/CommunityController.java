package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.PagenationDTO;

import com.jjeopjjeop.recipe.dto.UserDTO;
import com.jjeopjjeop.recipe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService service;

    @GetMapping
    public String all(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model){
        PagenationDTO pagenationDTO = getPagenationDTO(page,service.count());
        List<CommunityDTO> board = service.getBoard(pagenationDTO);

        model.addAttribute("board",board);
        model.addAttribute("page",pagenationDTO);
        return "community/index";
    }

    @GetMapping("/recipeReview")
    public String recipeReview(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model){
        PagenationDTO pagenationDTO = getPagenationDTO(page,service.recipeReviewCount());
        List<CommunityDTO> board = service.getRecipeReviews(pagenationDTO);

        model.addAttribute("board",board);
        model.addAttribute("page",pagenationDTO);
        return "community/recipeReview";
    }

    @GetMapping("/freeForum")
    public String freeForum(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model){
        PagenationDTO pagenationDTO = getPagenationDTO(page, service.freeForumCount());
        List<CommunityDTO> board = service.getFreeForums(pagenationDTO);

        model.addAttribute("board",board);
        model.addAttribute("page",pagenationDTO);
        return "community/freeForum";
    }

    private PagenationDTO getPagenationDTO(Integer page,int recordCount) {
        PagenationDTO pagenationDTO = new PagenationDTO();
        int startRow = (page != null) ? page : 0;
        int count = recordCount;
        int perPage = 10;
        int totalPageCnt = (int) Math.ceil((double) count/(double) perPage);
        int startPageNum = page >=totalPageCnt-3 ? totalPageCnt-4:Math.max(1, page -1);
        int endPageNum = page >=totalPageCnt-3 ? totalPageCnt: Math.min(startPageNum + 4, totalPageCnt);

        pagenationDTO.setPage(startRow);
        pagenationDTO.setCount(count);
        pagenationDTO.setTotalPageCnt(totalPageCnt);
        pagenationDTO.setPerPage(perPage);
        //db에 넘길 row num
        pagenationDTO.setStartRow(1 + (perPage * page));
        pagenationDTO.setEndRow(perPage*(page +1));
        //front에 넘길 페이지 번호
        pagenationDTO.setStartPageNum(startPageNum);
        pagenationDTO.setEndPageNum(endPageNum);

        log.info("start and end={},{}",startPageNum,endPageNum);

        return pagenationDTO;
    }

    @GetMapping("/form")
    public String form(@ModelAttribute("community") CommunityDTO community){
        //빈 객체 넘기기
        // form.html에 th:object를 썼기때문에 모델 어트리뷰트가 있어야함.
        // th:object를 쓰면 id name value 생략가능
        return "community/form";
    }

    @PostMapping("/form")
    public String forFormSubmit(@Validated @ModelAttribute("community") CommunityDTO community, BindingResult bindingResult, @RequestPart(value = "image",required=false) List<MultipartFile> image,HttpSession session){
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "community/form";
        }

        //성공로직
        //transaction 처리 필요 TODO
        UserDTO user = (UserDTO) session.getAttribute("user");
        String userId = user.getUser_id();
        community.setUser_id(userId);
        service.save(community);
        log.info("savedId={}",community.getId());
        if(image!=null){
            log.info("image={}",image.size());
            service.saveImagesToDB(image,community.getId());
        }

        return "redirect:/community/post?id="+community.getId();
    }

    @GetMapping("/post")
    public String post(Integer id, Model model, HttpSession session){

        UserDTO user = (UserDTO) session.getAttribute("user");
        String userId = user.getUser_id();
        CommunityDTO post = service.findPost(id,userId);

//        model.addAttribute("like",post.isLiked());
        model.addAttribute("community",post);

        return "community/post";
    }

    @GetMapping("/delete/{postId}")
    public String deletePostById(@PathVariable Integer postId){
        service.deletePost(postId);
        return "redirect:/community";
    }

    @GetMapping("/report/{postId}")
    public String reportPostById(@PathVariable Integer postId){
        service.reportPost(postId);

        return "redirect:/community/post?id="+postId;
    }

    //ajax
    @PostMapping("/like")
    public String updateLikeCnt(Model model, Integer postId, boolean add,HttpSession session){

        //라이크할때 라이크테이블에 유저아이디, 포스트아이디 추가 / 해당포스트 like ++
        //라이크 취소 라이크테이블에 유저아이디, 포스프아이디로된 데이터 삭제/ 해당 포스트 like --
        UserDTO user = (UserDTO) session.getAttribute("user");
        String userId = user.getUser_id();

        //코드 맘에안듦. TODO
        CommunityDTO post;
        if(add==true){
            service.addLikeCnt(postId,userId);
            post = service.findPost(postId, userId);
            post.setLiked(true);
        }else{
            service.subtractLikeCnt(postId,userId);
            post = service.findPost(postId, userId);
        }
        model.addAttribute("community",post);
        return "community/post :: #like-btn";
    }

}
