package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.form.CommunitySearchForm;
import com.jjeopjjeop.recipe.service.CommunityService;
import com.jjeopjjeop.recipe.service.RecipeService;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final RecipeService recipeService;

    @GetMapping
    public String all(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model){
        PagenationDTO pagenationDTO = getPagenationDTO(page, communityService.count());
        List<CommunityDTO> board = communityService.getBoard(pagenationDTO);

        model.addAttribute("board",board);
        model.addAttribute("page",pagenationDTO);
        //model.addAttribute("localDateTime", LocalDateTime.now());
        return "community/index";
    }

    @GetMapping("/recipeReview")
    public String recipeReview(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model){
        PagenationDTO pagenationDTO = getPagenationDTO(page, communityService.recipeReviewCount());
        List<CommunityDTO> board = communityService.getRecipeReviews(pagenationDTO);

        model.addAttribute("board",board);
        model.addAttribute("page",pagenationDTO);
        //model.addAttribute("localDateTime", LocalDateTime.now());
        return "community/recipeReview";
    }

    @GetMapping("/freeForum")
    public String freeForum(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model){
        PagenationDTO pagenationDTO = getPagenationDTO(page, communityService.freeForumCount());
        List<CommunityDTO> board = communityService.getFreeForums(pagenationDTO);

        model.addAttribute("board",board);
        model.addAttribute("page",pagenationDTO);
        //model.addAttribute("localDateTime", LocalDateTime.now());
        return "community/freeForum";
    }

    private PagenationDTO getPagenationDTO(Integer page,int recordCount) {
        PagenationDTO pagenationDTO = new PagenationDTO();
        int startRow = (page != null) ? page : 0; // page가 입력되지 않았으면 자동으로 1페이지로(0이 1)
        int count = recordCount;// 전체 글 개수
        int perPage = 10;//한페이지당 보여질 글 개수
        int totalPageCnt = (int) Math.ceil((double) count/(double) perPage); // 전체글 / 페이지에 보여질 개수 = 페이지 수
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
    public String form(@ModelAttribute("community") CommunityDTO community, Model model){
        //빈 객체 넘기기
        // form.html에 th:object를 썼기때문에 모델 어트리뷰트가 있어야함.
        // th:object를 쓰면 id name value 생략가능
        List<RecipeDTO> recipes = Collections.emptyList();
        model.addAttribute("recipes",recipes);
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
        UserDTO user = getUser(session);
        String userId = user.getUser_id();
        community.setUser_id(userId);

        //자유글이면 레시피가 선택됐더라도 선택X
        if(community.getCategory().equals("0")){
            community.setRcp_seq(0);
        }

        //로직 매우 맘에안듦.
        //이미지 테이블에 포스트 아이디로 된 이미지가있는지 확인하는 로직을 짜도 되긴되지만
        //그게 더 속도가 느릴껏같음.
        if(image!=null){
            community.setImage_exists(1);
        }
        communityService.save(community,image);


        return "redirect:/community/post?id="+community.getId();
    }

    //ajax
    @PostMapping("/form/searchRecipe")
    public String searchKeyword(String searchKey, Model model){
        List<RecipeDTO> recipes = recipeService.searchListByKeyword(searchKey);
        model.addAttribute("recipes",recipes);
       return "community/form :: .recipe-box";
    }

    @GetMapping("/post")
    public String post(Integer id, Model model, HttpSession session){

        UserDTO user = getUser(session);
        String userId = user.getUser_id();
        //로그인한 유저가 해당 포스트 좋아요 했는지도 확인함.
        CommunityDTO post = communityService.findPostWithLikeInfo(id,userId);
        List<CommunityCommentDTO> comments = communityService.findComments(id);

        //글이 레시피 후기글이면 레시피 정보도 넘기기.
        Integer rep_scq = post.getRcp_seq();
        if(rep_scq != null && rep_scq !=0){
            model.addAttribute("recipe",recipeService.contentProcess(rep_scq));
        }
        //로그인한 유저의 정보도 뷰에 넘김.
        model.addAttribute("user",user);
        //포스트
        model.addAttribute("community",post);
        //댓글
        model.addAttribute("comments",comments);

        return "community/post";
    }

    private UserDTO getUser(HttpSession session) {
        return (UserDTO) session.getAttribute("user");
    }


    @GetMapping("/edit/{postId}")
    public String editPostById(@PathVariable Integer postId, Model model){
        CommunityDTO post = communityService.findPostById(postId);

        List<RecipeDTO> recipes = Collections.emptyList();
        model.addAttribute("community",post);
        model.addAttribute("recipes",recipes);

        return "community/edit";
    }

    @PostMapping("/edit/{postId}")
    public String editPostByIdSubmit(@PathVariable Integer postId, CommunityDTO community, @RequestPart(value = "image",required=false) List<MultipartFile> image){
        //,
        //        IMAGE_EXISTS = #{image_exists} mapper 부분 처리
        log.info("getContent={}",community.getContent());
        log.info("getId={}",community.getId());
        log.info("getTitle={}",community.getRcp_seq());
        if(!image.isEmpty()){
            communityService.deleteCurrentImages(postId);
        }

        community.setId(postId);
        communityService.editPost(community,image);
        return "redirect:/community/post?id="+postId;
    }

    @GetMapping("/delete/{postId}")
    public String deletePostById(@PathVariable Integer postId){
        communityService.deletePost(postId);
        return "redirect:/community";
    }

    @GetMapping("/report/{postId}")
    public String reportPostById(@PathVariable Integer postId){
        communityService.reportPost(postId);
        return "redirect:/community/post?id="+postId;
    }

    //좋아요
    //ajax
    @ResponseBody
    @PostMapping("/like")
    public Integer updateLikeCnt(Integer postId, boolean add,HttpSession session){

        UserDTO user = getUser(session);
        String userId = user.getUser_id();

        //코드 맘에안듦. TODO
        CommunityDTO post;
        if(add==true){
            communityService.addLikeCnt(postId,userId);
            post = communityService.findPostWithLikeInfo(postId, userId);
            post.setLiked(true);
        }else{
            communityService.subtractLikeCnt(postId,userId);
            post = communityService.findPostWithLikeInfo(postId, userId);
        }
       // model.addAttribute("community",post);
        return post.getLike_count();
    }

    //댓글
    @PostMapping("/post/comment")
    public String submitComment(CommunityCommentDTO communityCommentDTO,HttpSession session, HttpServletRequest request){
        UserDTO user = getUser(session);
        String userId = user.getUser_id();
        communityCommentDTO.setUser_id(userId);
        communityService.postComment(communityCommentDTO);

        String refererLink = request.getHeader("referer");
        log.info(refererLink);
        return "redirect:"+refererLink;
    }

    //댓글수정
    //ajax
    @ResponseBody
    @PostMapping("/post/comment/edit")
    public void editComment(Integer commentId, String content){

        communityService.editComment(Map.of("commentId",commentId,"content",content));

    }

    @GetMapping("/post/comment/delete")
    public String deleteComment(Integer commentId, HttpServletRequest request){
        communityService.deleteComment(commentId);
        String refererLink = request.getHeader("referer");

        return "redirect:"+refererLink;
    }

    @GetMapping("/post/comment/report")
    public String reportComment(Integer commentId, HttpServletRequest request){
        communityService.reportComment(commentId);
        String refererLink = request.getHeader("referer");

        return "redirect:"+refererLink;
    }


    //상세검색
    int totalCnt;
    CommunitySearchForm form;
    @GetMapping("/search")
    public String detailSearch(@ModelAttribute("searchForm") CommunitySearchForm searchForm,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                               Model model, HttpServletRequest request){

        String referer = request.getHeader("referer");
        if(referer.contains("/community/search")){
            log.info("form={}",form);
            List<CommunityDTO> communityBySearch = communityService.findCommunityBySearch(form, getPagenationDTO(page, totalCnt));
            model.addAttribute("board",communityBySearch);
        }

        PagenationDTO pagenationDTO = getPagenationDTO(page, totalCnt);
        model.addAttribute("page",pagenationDTO);

        return "community/detailSearch";
    }

    @PostMapping("/search")
    public String detailSearchSubmit(CommunitySearchForm searchForm){
        totalCnt = communityService.countCommunityBySearch(searchForm);
        form=searchForm;
        return "redirect:/community/search";
    }



}
