package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.PageDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.service.ProduceService;
import com.jjeopjjeop.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecipeService recipeService;
    private final ProduceService produceService;

    @GetMapping
    public String index(Model model){
        //임의로 해놓음. 원래 인기순 불러와야함.
        RecipePageDTO recipePageDTO = new RecipePageDTO();
        recipePageDTO.setStartPage(0);
        recipePageDTO.setEndPage(3);

        List<RecipeDTO> rcpList = recipeService.listProcess(recipePageDTO);
        List<ProduceDTO> list = produceService.produceListProcess(new PageDTO());
        model.addAttribute("rcpList",rcpList);
        model.addAttribute("list",list);

        return "index";
    }
}
