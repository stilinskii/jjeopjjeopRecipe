package com.jjeopjjeop.recipe.restAPI;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community")
@CrossOrigin("*")
public class CommunityApiController {

    @GetMapping
    public String testrest(){
        return "hi!";
    }
}
