package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.service.KakaoPay;
import com.jjeopjjeop.recipe.service.PayService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Slf4j
@Controller
public class PayController {

    @Autowired
    private PayService payService;

    public PayController() {

    }
    @Autowired
    private ProduceDTO produceDTO;

    //판매글 상세페이지에서 버튼 클릭하여 장바구니에 넣기
    @GetMapping("/cart/write")
    public String cartWrite(PayDTO payDTO){
        log.info("dto={}", payDTO.getProduce_num());
        payService.cartWriteProcess(payDTO);
        log.info("dto={}", payDTO.getProduce_num());
        return "redirect:/produce/list";
    }

    //본인 마이페이지 들어가서 장바구니 보기
    @GetMapping("/mypage/cart/view/{user_id}")
    public ModelAndView cartView(@PathVariable("user_id") String user_id, ModelAndView mav){
        List<ProduceDTO> list = payService.cartView(user_id);
        mav.addObject("list", list);
        mav.setViewName("/users/cart");

        return mav;
    }

    //본인 마이페이지 들어가서 장바구니 항목 삭제
    @GetMapping("/mypage/cart/delete/{user_id}/{pay_num}")
    public String cartDelete(@PathVariable("user_id") String user_id, @PathVariable("pay_num") int pay_num){
        payService.cartDelete(pay_num);
        return "redirect:/mypage/cart/view/" + user_id;
    }
////카카오페이시작///////////////////////////////////////////////////////////////////////////////////////
    //카카오페이 결제
    @Setter(onMethod_ = @Autowired)
    private KakaoPay kakaopay;

    //이유는 모르겠는데, 얘없으면 에러나옴. 큐알코드도 안나옴.
    //?오늘은 없어도 잘만된다.
//    @GetMapping("/kakaoPay")
//    public void kakaoPayGet() {
//    }
//결제요청
    @PostMapping("/kakaoPay/{pay_num}")
    public String kakaoPay(@PathVariable("pay_num") int pay_num) {
        ProduceDTO produceDTO = payService.payInfo(pay_num);

        return "redirect:" + kakaopay.kakaoPayReady(produceDTO); //큐알코드 화면 나옴.
    }
//결제완료
    //kakaoPaySuccess()이거 어떻게 호출???
    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token, produceDTO)); //java.lang.NullPointerException
    } //org.springframework.web.client.HttpClientErrorException$BadRequest: 400 Bad Request: "{"msg":"partner_order_id can't be null.","code":-2}"
/*
@RequestParam("produceDTO") ProduceDTO produceDTO 넣어봄.

[org.springframework.web.bind.MissingServletRequestParameterException: Required request parameter 'produceDTO' for method parameter type ProduceDTO is not present]
 get말고 post로 받아와야한다. 
 */


////카카오페이 끝///////////////////////////////////////////////////////////////////////////////////////
    //결제에 필요한 정보 담기.

    @GetMapping("/buy/payInfo/{pay_num}")
    public String payInfo(@PathVariable("pay_num") int pay_num){
        System.out.println("===============******************=====================******************");
        ProduceDTO produceDTO = payService.payInfo(pay_num);

        System.out.println(produceDTO);

        System.out.println("================******************====================******************");
        return "index";
    }
//////////////////////////////////////////////////
    //임시 결제완료페이지 kakaoPaySuccess
    @GetMapping("/produce/temp")
    public String produceTemp(){
        return "produce/try";
    }
    
   //결제후 pay를 1로 바꿔주기.
    @PostMapping("/mypage/cart/update/{pay_num}")
    public String cartUpdate(@PathVariable("pay_num") int pay_num){
        payService.cartUpdate(pay_num);

        return "redirect:/produce/list";
    }


}