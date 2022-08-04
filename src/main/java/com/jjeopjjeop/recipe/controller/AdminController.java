package com.jjeopjjeop.recipe.controller;


import com.jjeopjjeop.recipe.dto.A_userDTO;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.dto.Community;
import com.jjeopjjeop.recipe.dto.SellerDTO;
import com.jjeopjjeop.recipe.service.AdminService;
import com.jjeopjjeop.recipe.service.CommunityService;
import com.jjeopjjeop.recipe.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/admin/*")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final SellerService sellerService;
    private final CommunityService service;

    //db 확인
//    @GetMapping(value = "/templates/practice")
//    public String AdminListMethod(){
//        int totalRecord = adminService.countProcess();
//        System.out.println(totalRecord);
//
//        return "practice";
//    }

    //회원목록 불러오기
//    @RequestMapping(value= "/admin/userlist", method = RequestMethod.GET)
//    public ModelAndView listUser(ModelAndView mav, HttpServletRequest request) throws Exception {
//        String viewName = (String)request.getAttribute("viewName");
//        System.out.println("list viewName :" + viewName);
//        mav.addObject("listUser", adminService.listUser());
//        mav.setViewName("/admin/userlist");
//        return mav;
//    }

    @GetMapping
      public String userAll(Model model) {
          List<A_userDTO> users = adminService.listUser();
          model.addAttribute("users", users);
          return "admin/userlist";
    }



    //회원 인서트(문제)
//    @RequestMapping(value = "/admin/view")
//    public ModelAndView addUser(@ModelAttribute("adminDTO") AdminDTO adminDTO,
//                                HttpServletRequest request, HttpServletResponse response) throws Exception {
//        int result = 0;
//        result = adminService.addUser(adminDTO);
//        ModelAndView mav = new ModelAndView("redirect:/admin/userlist");
//        return mav;
//
//    }

    //회원 삭제하기


    //판매자 목록 불러오기(SellerController랑 겹친다)
//    @GetMapping
//    public String allSell(Model model){
//        List<SellerDTO> sellers = sellerService.getSeller();
//        model.addAttribute("sellers",sellers);
//        //변경?
//        return "seller/form";
//    }

    //판매자 승인
//    public String appro(String user_id, Model model){
//
//    }

    //게시판 전체/신고목록 및 삭제
//    @GetMapping
//    public String appcomm(Model model){
//        List<Community> appboard = adminService.appcomList();
//        model.addAttribute("appboard", appboard);
//        return "admin/s_index";
//    }

    //게시판 삭제 잘 모르겠다...
//    @DeleteMapping("delcomm")
//    public String delcomm(Model model, @RequestParam("id") Integer id, @ModelAttribute Community board){
//         adminService.delcomm(id);
//        //model.addAttribute("delcomm", delcomm);
//        return "redirect:admin/c_index";
//
//    }


    //레시피 신고목록


    //레시피 삭제


}
