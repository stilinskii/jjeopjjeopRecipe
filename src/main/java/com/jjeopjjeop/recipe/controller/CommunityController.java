package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService service;

    @GetMapping
    public String all(Model model){
        log.info("cnt={}",service.count());
        List<CommunityDTO> board = service.getBoard();
        model.addAttribute("board",board);
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
            service.saveImages(image,community.getId());
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
