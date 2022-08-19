package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.service.RecipeAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecipeAPIController {
    @Autowired
    private RecipeAPIService recipeAPIService;

    @GetMapping("recipe/apilist")
    public ModelAndView list(ModelAndView mav){
        mav.addObject("recipeApiList", recipeAPIService.list(0));
        mav.setViewName("/recipe/apiTest");

        return mav;
    }

    @GetMapping("recipe/apiview/{rcp_seq}")
    public ModelAndView view(ModelAndView mav, @PathVariable("rcp_seq") Integer rcp_seq){
        mav.addObject("rcp", recipeAPIService.view(rcp_seq));
        mav.addObject("manualList", recipeAPIService.view(rcp_seq).getManualDTOList());
        mav.setViewName("/recipe/apiView");
        return mav;
    }
}
