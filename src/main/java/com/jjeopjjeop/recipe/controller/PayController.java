package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class PayController {

    @Autowired
    private PayService payService;

    public PayController() {

    }
    @Autowired
    private ProduceDTO produceDTO2;

    //판매글 상세페이지에서 버튼 클릭하여 장바구니에 넣기
    @GetMapping("/cart/write")
    public String cartWrite(PayDTO payDTO){
        log.info("dto={}", payDTO.getProduce_num());
        payService.cartWriteProcess(payDTO);
        log.info("dto={}", payDTO.getProduce_num());
        return "redirect:/produce/list";
    }

    //본인 마이페이지 들어가서 장바구니 보기
//    @GetMapping("/mypage/cart/view/{user_id}")
//    public ModelAndView cartView(@PathVariable("user_id") String user_id, ModelAndView mav){
//        List<ProduceDTO> list = payService.cartView(user_id);
//        mav.addObject("list", list);
//        mav.setViewName("/users/cart");
//
//        return mav;
//    }
    private int currentPage;

    @GetMapping("/mypage/cart/view/{user_id}")
    public ModelAndView cartView(@PathVariable("user_id") String user_id, ModelAndView mav, RecipePageDTO recipePageDTO){
        // 전체 레코드 수
        int totalRecord = payService.cartCount();

        if(totalRecord>0){//전체 레코드 수가 0개보다 많으면
            //현재페이지와 1중에 큰 것을 currentPage에 넣음.게시판에 들어오고 아무것도 안누르면 currentPage 0이니까
            currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);

            recipePageDTO = new RecipePageDTO(currentPage, totalRecord);  //이제 startrow, endrow 계산됨.
        }


        mav.addObject("totalRecord", totalRecord); //전체 레코드 정보 넘기기
        List<ProduceDTO> list = payService.cartView(recipePageDTO);
        mav.addObject("list", list);
        mav.addObject("pDto", recipePageDTO); //페이지 정보 넘겨주기
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
        produceDTO2.setPayDTO(produceDTO.getPayDTO());
        produceDTO2.setUser_id(produceDTO.getUser_id());
        produceDTO2.setProduce_name(produceDTO.getProduce_name());
        List<PayDTO> payDTOList = new ArrayList<>();
        for(PayDTO payDTO : produceDTO.getPayDTOList()){
            payDTOList.add(payDTO);
        }
        produceDTO2.setPayDTOList(payDTOList);
        return "redirect:" + kakaopay.kakaoPayReady(produceDTO); //큐알코드 화면 나옴.
    }


    /*

     for(PayDTO payDTO : produceDTO.getPayDTOList()){
            params.add("partner_order_id", payDTO.getPay_num().toString());  //String만 들어가서 String으로 바꿔줘야함.
            params.add("quantity", payDTO.getQuantity().toString());
            params.add("total_amount", payDTO.getTotal_price().toString());
        };
        params.add("cid", "TC0ONETIME");

        params.add("partner_user_id", produceDTO.getUser_id());
        params.add("item_name", produceDTO.getProduce_name());
     */
//결제완료
    //kakaoPaySuccess()이거 어떻게 호출???
    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token, produceDTO2)); //java.lang.NullPointerException
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