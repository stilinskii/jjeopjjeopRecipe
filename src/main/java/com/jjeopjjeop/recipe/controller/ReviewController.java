package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    public ReviewController() {

    }

    @GetMapping("/review/write")
    public String reviewWrite(){

        return "/produce/reviewWrite";
    }

    //@PostMapping("/review/delete/{payNum}")  <-이걸로 하면 오류남(There was an unexpected error (type=Method Not Allowed, status=405).
    //Request method 'GET' not supported)
    @GetMapping("/review/delete/{payNum}")
    public String reviewDelete(@PathVariable("payNum") int pay_num, RedirectAttributes redirectAttributes){
        reviewService.reviewDelete(pay_num);

        redirectAttributes.addFlashAttribute("message","Category deleted");
        redirectAttributes.addFlashAttribute("alertClass","alert-success");

        return "redirect:/produce/list";

    }
}
