package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.CommunityBoardDTO;
import com.jjeopjjeop.recipe.service.CommunityBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class CommunityBoardController {

    private final CommunityBoardService service;

    @GetMapping
    public String all(Model model){
        List<CommunityBoardDTO> board = service.getBoard();
        model.addAttribute("board",board);
        return "community/index";
    }

    @GetMapping("/form")
    public String form(@Validated @ModelAttribute CommunityBoardDTO communityBoardDTO){

        return "community/form";
    }

    @PostMapping("/form")
    public String forFormSubmit(@Validated @ModelAttribute CommunityBoardDTO dto){
        log.info("dto={}",dto.getTitle());
        service.insert(dto);

        return "redirect:/community";
    }
}
