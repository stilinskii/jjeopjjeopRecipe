package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.CommunityBoardDTO;
import com.jjeopjjeop.recipe.service.CommunityBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityBoardController {

    private final CommunityBoardService service;

    @GetMapping
    public String all(Model model){
        List<CommunityBoardDTO> board = service.getBoard();
        model.addAttribute("board",board);
        return "communityBoard/index";
    }
}
