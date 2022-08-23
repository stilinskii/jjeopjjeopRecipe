package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.SellerDTO;
import com.jjeopjjeop.recipe.dto.UserDTO;
import com.jjeopjjeop.recipe.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
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

    @GetMapping("/seller/form")
    public String all(Model model){
        //List 뒤는 html에서 참조?
        List<SellerDTO> sellers = sellerService.getSeller();
        model.addAttribute("sellers", sellers);
        return "seller/form";
    }

    //insert

    @GetMapping("/seller/write")
    public String form(){
        return "/seller/write";
    }

    private UserDTO getUser(HttpSession session){
        return (UserDTO) session.getAttribute("user");
    }

    @PostMapping("/seller/write")
    public String forFormSubmit(@Validated @ModelAttribute("sellerDTO") SellerDTO sellerDTO, BindingResult bindingResult, HttpSession session){
//        sellerDTO.setUser_id(sellerDTO.getUser_id());
//        sellerDTO.getBusiness_name(sellerDTO.getBusiness_name());
        UserDTO userDTO = getUser(session);
        String user_id = userDTO.getUser_id();
        sellerDTO.setUser_id(user_id);

        //유효성검사
        if(bindingResult.hasErrors()){
            log.info("erros={}", bindingResult);
            return "/seller/form";
        }

        sellerService.save(sellerDTO);
        return "redirect:/produce/list";

    }


}