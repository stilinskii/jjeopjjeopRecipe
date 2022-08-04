package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.SellerDTO;
import com.jjeopjjeop.recipe.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/seller")
@Controller
@RequiredArgsConstructor
public class SellerController {

     private final SellerService sellerService;

//    @Autowired
//    private SellerService sellerService;
//
//    @Autowired
//    private SellerDTO sellerDTO;


//    @RequestMapping(value = "/seller/view", method = RequestMethod.GET)
//    public ModelAndView listSeller(ModelAndView mav, HttpServletRequest request) throws Exception{
//        String viewName = (String)request.getAttribute("viewName");
//        System.out.println("list viewName :" + viewName);
//        mav.addObject("sellerList", sellerService.listSeller());
//        mav.setViewName("/templates/seller/view");
//        return mav;
//            }

//    @RequestMapping(value = "/seller/write", method = RequestMethod.POST)
//    public ModelAndView insertSeller(@ModelAttribute("member") SellerDTO sellerDTO, HttpServletRequest request,
//                                     HttpServletResponse response) throws Exception {
//        //request.setCharacterEncoding("utf-8");// 자동 인코딩이 된다.
//        int result = 0;
//        result = sellerService.insertSeller(sellerDTO);
//        ModelAndView mav = new ModelAndView("redirect:/seller/view");
//        return mav;
//    }

    @GetMapping("/form")
    public String all(Model model){
        //List 뒤는 html에서 참조?
        List<SellerDTO> sellers = sellerService.getSeller();
        model.addAttribute("sellers", sellers);
        return "seller/form";
    }

    //insert부분 알아봐야함
    //form을 리턴하는 문제 발생 해결해야함->해결
    @GetMapping("/write")
    public String form(@ModelAttribute SellerDTO sellerDTO){
        return "seller/write";
    }

    @PostMapping("/write")
    public String forFormSubmit(@ModelAttribute SellerDTO sellerDTO, BindingResult bindingResult){
//        sellerDTO.setUser_id(sellerDTO.getUser_id());
//        sellerDTO.getBusiness_name(sellerDTO.getBusiness_name());
        sellerService.save(sellerDTO);
        return "seller/write";

    }


    }