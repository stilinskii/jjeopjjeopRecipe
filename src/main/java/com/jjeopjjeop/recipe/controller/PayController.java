package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class PayController {

    @Autowired
    private PayService payService;

    public PayController() {

    }

    @GetMapping("/cart/write")
    public String cartWrite(PayDTO payDTO){
        log.info("dto={}", payDTO.getProduce_num());
        payService.cartWriteProcess(payDTO);
        log.info("dto={}", payDTO.getProduce_num());
        return "redirect:/produce/list";
    }
}
//service는 사용자가 이해하기 편하게.