package com.jjeopjjeop.recipe.controller;



import com.jjeopjjeop.recipe.dto.*;

import com.jjeopjjeop.recipe.dto.A_userDTO;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.SellerDTO;

import com.jjeopjjeop.recipe.service.AdminService;
import com.jjeopjjeop.recipe.service.CommunityService;
import com.jjeopjjeop.recipe.service.RecipeService;
import com.jjeopjjeop.recipe.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

//    @Autowired
//   private int currentPage;




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
/*
    //회원목록
    @GetMapping("/u_index")
      public String UserList(Model model, PageDTO pageDTO) {
        log.info("회원 리스트");
        int totalUSer = adminService.countUser();

        if(totalUSer>0){
            currentPage = Math.max(pageDTO.getCurrentPage(),1);
            pageDTO = new PageDTO(currentPage, totalUSer);
        }

//        List<UserDTO> users = adminService.USerList(pv);
//         model.addAttribute("users", users);
        model.addAttribute("count", totalUSer);
        model.addAttribute("users", adminService.UserList(pageDTO));
        model.addAttribute("pdto", pageDTO);

          return "admin/u_index";
    }
*/
    //회원상세
    @GetMapping("/detailU")
    public String detailU(String user_id, Model model){
        UserDTO detailU = adminService.detailUser(user_id);
        model.addAttribute("duser", detailU);
        return "admin/detailU";
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
    @GetMapping("delU/{user_id}")
    public String delUser(@PathVariable String user_id){
        adminService.delUser(user_id);
        return "redirect:/admin/u_index";
    }



    //미승인 판매자 목록 불러오기(=0)
    @GetMapping("/s_index")
    public String allNSell(Model model){
        List<SellerDTO> nsellers = adminService.listNSeller();
        model.addAttribute("nsellers",nsellers);
        return "admin/s_index";
    }

    //승인 판매자 목록
    @GetMapping("/ss_index")
    public String allSell(Model model){
        List<SellerDTO> sellers = adminService.listSeller();
        model.addAttribute("sellers", sellers);
        return "admin/ss_index";
    }


    //판매자 승인
    @GetMapping("upSel/{user_id}")
    public String appro(@PathVariable String user_id){
       adminService.approSeller(user_id);
       return "redirect:/admin/ss_index";
    }

    //레시피 목록
    @GetMapping("/r_index")
    public String apprcp(Model model){
        List<RecipeDTO> apprcplist =adminService.apprcpList();
        model.addAttribute("apprcplist", apprcplist);
        return "admin/r_index";
    }

    //레시피글 누르면 상세내용 페이지
//    @GetMapping("/")
//    public String rcpdetail(String rcp_name, Model model){
//        model.addAttribute("RecipeDTO", rcpdetail);
//        return "admin/";
//    }




    //레시피 게시글 삭제

    //레시피 삭제
    @GetMapping("delrcp/{user_id}")
    public  String delRcp(@PathVariable String user_id){
        adminService.delRcp(user_id);
        return "redirect:/admin/r_index";
    }

    //게시판 전체/신고목록 및 삭제
    @GetMapping("/c_index")
    public String appcomm(Model model){
        List<CommunityDTO> appboard = adminService.appcomList();
        model.addAttribute("appboard", appboard);
        return "admin/c_index";
    }

    //게시판 삭제 성공
    @GetMapping("/delcom/{id}")
    public String delcomm(@PathVariable Integer id){
        log.info("access");
         adminService.delcomm(id);
        //model.addAttribute("delcomm", delcomm);
        return "redirect:/admin/c_index";

    }

    //레시피 신고목록(recipe/view 페이지를 갖고오고싶은데)






}
