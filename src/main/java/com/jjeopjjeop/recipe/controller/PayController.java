package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.config.MySecured;
import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.KakaoPay;
import com.jjeopjjeop.recipe.service.PayService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @MySecured //로그인안한상태로 클릭시 로그인 페이지로 넘어가게
    @PostMapping("/cart/write")
    public String cartWrite(PayDTO payDTO){
        payService.cartWriteProcess(payDTO);
        return "redirect:/produce/view/" + payDTO.getProduce_num();
    }

   private int currentPage;

    @MySecured
    @GetMapping("/mypage/cart/view")
    public ModelAndView cartView(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,ModelAndView mav, HttpServletRequest request){

        int totalRecord = payService.cartCount(request);// 전체 레코드 수
        Pagenation pagenation = new Pagenation(page,5, totalRecord); //페이지 처리를 위한 계산

        mav.addObject("list", payService.cartView(pagenation));
        mav.addObject("page", pagenation); //페이지 정보 넘겨주기
        mav.setViewName("/users/cart");

        return mav;
    }

    //구매내역 보기
    @MySecured
    @GetMapping("/mypage/pay/view")
    public ModelAndView payView(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, ModelAndView mav, HttpServletRequest request){
        // 전체 레코드 수
        int totalRecord = payService.payCount(request);
        Pagenation pagenation = new Pagenation(page,5, totalRecord); //페이지 처리를 위한 계산

        mav.addObject("list", payService.payView(pagenation));
        mav.addObject("page", pagenation); //페이지 정보 넘겨주기
        mav.setViewName("/users/payment");

        return mav;
    }


    //본인 마이페이지 들어가서 장바구니 항목 삭제
    @MySecured
    @GetMapping("/mypage/cart/delete/{pay_num}")
    public String cartDelete(@PathVariable("pay_num") int pay_num){
        payService.cartDelete(pay_num);
        return "redirect:/mypage/cart/view";
    }
////카카오페이시작///////////////////////////////////////////////////////////////////////////////////////
    //카카오페이 결제
    @Setter(onMethod_ = @Autowired)
    private KakaoPay kakaopay;

    //판매글 상세페이지에서 버튼 클릭하여 장바구니에 넣고 바로 결제하기
    @MySecured
    @PostMapping("/kakaoPayDirect")
    public String kakaoPayDirect(PayDTO payDTO, HttpServletRequest request){
        //장바구니에 넣음.
        payService.cartWriteProcess(payDTO);

        //pay_history에서 결제에 필요한 정보 가져오기.
        HttpSession session =  request.getSession();
        int payNum = payService.cartSelect((String) session.getAttribute("user_id"));

        //바로 카카오페이결제시작
        ProduceDTO produceDTO = payService.payInfo(payNum);
        produceDTO2.setPayDTO(produceDTO.getPayDTO());
        produceDTO2.setUser_id(produceDTO.getUser_id());
        produceDTO2.setProduce_name(produceDTO.getProduce_name());
        List<PayDTO> payDTOList = new ArrayList<>();
        for(PayDTO payDTO2 : produceDTO.getPayDTOList()){
            payDTOList.add(payDTO2);
        }
        produceDTO2.setPayDTOList(payDTOList);


        return "redirect:" + kakaopay.kakaoPayReady(produceDTO); //큐알코드 화면 나옴.
    }


    //결제요청
    @MySecured
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

    //결제완료
    //kakaoPaySuccess()이거 어떻게 호출???
    @MySecured
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
    
   //결제성공후 pay를 1로 바꿔주기.
   @MySecured
    @PostMapping("/mypage/cart/update/{pay_num}")
    public String cartUpdate(@PathVariable("pay_num") int pay_num){
        payService.cartUpdate(pay_num);
        return "redirect:/produce/list";
    }


}