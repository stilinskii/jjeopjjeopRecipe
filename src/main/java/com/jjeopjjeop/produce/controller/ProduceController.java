package com.jjeopjjeop.produce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProduceController {

    @GetMapping("/one")
    public String main(){
        return "one";
    }

}
