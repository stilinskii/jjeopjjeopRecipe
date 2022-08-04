package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.Community;
import com.jjeopjjeop.recipe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService service;

    @GetMapping
    public String all(Model model){
        List<Community> board = service.getBoard();
        model.addAttribute("board",board);
        return "community/index";
    }

    @GetMapping("/form")
    public String form(@ModelAttribute Community community){
        //빈 객체 넘기기
        // form.html에 th:object를 썼기때문에 모델 어트리뷰트가 있어야함.
        // th:object를 쓰면 id name value 생략가능
        return "community/form";
    }

    @PostMapping("/form")
    public String forFormSubmit(@Validated @ModelAttribute Community community, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "community/form";
        }

        //성공로직
        service.save(community);
        return "community/post";
        //mybatis insert에서 community return하게 해야함
//        Community savedPost = service.save(community);
//        return "redirect:/community/post?id="+savedPost.getId();
    }

    @GetMapping("/post")
    public String post(Integer id,Model model){

        Community post = service.findPostById(id);
        model.addAttribute("community",post);

        return "community/post";
    }

}
