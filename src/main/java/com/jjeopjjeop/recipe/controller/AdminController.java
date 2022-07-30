package com.jjeopjjeop.recipe.controller;


import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    //db 확인
    @GetMapping(value = "/templates/practice")
    public String AdminListMethod(){
        int totalRecord = adminService.countProcess();
        System.out.println(totalRecord);

        return "practice";
    }

    //회원목록 불러오기
    @RequestMapping(value= "/admin/userlist", method = RequestMethod.GET)
    public ModelAndView listUser(ModelAndView mav, HttpServletRequest request) throws Exception {
        String viewName = (String)request.getAttribute("viewName");
        System.out.println("list viewName :" + viewName);
        mav.addObject("listUser", adminService.listUser());
        mav.setViewName("/admin/userlist");
        return mav;
    }

    //회원 인서트
    @RequestMapping(value = "/admin/view")
    public ModelAndView addUser(@ModelAttribute("adminDTO") AdminDTO adminDTO,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        int result = 0;
        result = adminService.addUser(adminDTO);
        ModelAndView mav = new ModelAndView("redirect:/admin/userlist");
        return mav;

    }

}
