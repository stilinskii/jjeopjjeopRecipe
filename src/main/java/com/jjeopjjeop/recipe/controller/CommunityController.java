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
    public String form(@ModelAttribute Community communityBoardDTO){

        return "community/form";
    }

    @PostMapping("/form")
    public String forFormSubmit(@Validated @ModelAttribute Community dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "community/form";
        }

        service.insert(dto);

        return "redirect:/community";
    }
}
