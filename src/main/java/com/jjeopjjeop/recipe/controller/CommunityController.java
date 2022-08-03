package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.PagenationDTO;
import com.jjeopjjeop.recipe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService service;

    @GetMapping
    public String all(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model){
        PagenationDTO pagenationDTO = new PagenationDTO();
        int startPage = (page != null) ? page : 0;
        int count = service.count();
        double endPage = Math.ceil((double) count/(double) pagenationDTO.getPerPage());
        pagenationDTO.setPage(startPage);
        pagenationDTO.setCount(count);
        pagenationDTO.setTotalPageCnt((int)endPage);
        pagenationDTO.setStartPage(1 + (10 * (page)));
        pagenationDTO.setEndPage(10*(page+1));

        log.info("start and end = {},{}",pagenationDTO.getStartPage(),pagenationDTO.getEndPage());

        List<CommunityDTO> board = service.getBoard(pagenationDTO);

        model.addAttribute("board",board);
        model.addAttribute("page",pagenationDTO);
        return "community/index";
    }

    @GetMapping("/form")
    public String form(@ModelAttribute("community") CommunityDTO community){
        //빈 객체 넘기기
        // form.html에 th:object를 썼기때문에 모델 어트리뷰트가 있어야함.
        // th:object를 쓰면 id name value 생략가능
        return "community/form";
    }

    @PostMapping("/form")
    public String forFormSubmit(@Validated @ModelAttribute("community") CommunityDTO community, BindingResult bindingResult, @RequestPart(value = "image",required=false) List<MultipartFile> image){
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "community/form";
        }

        //성공로직
        //transaction 처리 필요 TODO
        service.save(community);
        log.info("savedId={}",community.getId());
        if(image!=null){
            log.info("image={}",image.size());
            service.saveImagesToDB(image,community.getId());
        }

        return "redirect:/community/post?id="+community.getId();
    }

    @GetMapping("/post")
    public String post(Integer id,Model model){

        CommunityDTO post = service.findPostById(id);
        model.addAttribute("community",post);

        return "community/post";
    }

}
