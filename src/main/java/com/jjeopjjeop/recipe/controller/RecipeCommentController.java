package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.RecipeCommentDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.service.RecipeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecipeCommentController {
    @Autowired
    private RecipeCommentService service;
    private int currentPage;

    @PostMapping("/recipe/comment/write")
    public String writeMethod(int rcp_seq, RecipeCommentDTO recipeCommentDTO){
        recipeCommentDTO.setRcp_seq(rcp_seq);
        recipeCommentDTO.setUser_id("테스트");
        service.writeProcess(recipeCommentDTO);

        return "redirect:/recipe/view/"+rcp_seq;
    }
}
