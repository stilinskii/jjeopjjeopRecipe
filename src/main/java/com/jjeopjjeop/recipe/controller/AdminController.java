package com.jjeopjjeop.recipe.controller;



import com.jjeopjjeop.recipe.config.MySecured;
import com.jjeopjjeop.recipe.dto.*;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.SellerDTO;

import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.AdminService;
import com.jjeopjjeop.recipe.service.CommunityService;
import com.jjeopjjeop.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final CommunityService communityService;
    private final RecipeService service;


    //관리자 페이지 실행
    @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("/main")
    public String admin(){
        return "admin/main";
    }


    //page
   // @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("/u_index")
    public String UserList(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model,String keyword) {
        log.info("회원 리스트");
       Pagenation pagenation = new Pagenation(page, 10, adminService.countUser(new Pagenation()));
       pagenation.setKeyword(keyword);
       List<UserDTO> users = adminService.userList(pagenation);
        model.addAttribute("users", users);
        model.addAttribute("page",pagenation);
        return "admin/u_index";
    }

    //회원상세
    @GetMapping("/detailU")
    public String detailU(String user_id, Model model){
        UserDTO detailU = adminService.detailUser(user_id);
        model.addAttribute("duser", detailU);
        return "admin/detailU";
    }

    //회원 삭제하기(키가 연결되어서 안 됨)
    // @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("delU/{user_id}")
    public String delUser(@PathVariable String user_id){
        log.info("user_id={}",user_id);
        adminService.delUser(user_id);
        return "redirect:/admin/u_index";
    }

    //회원 업데이트
    @GetMapping("upU/{user_id}")
    public String updateUser (@PathVariable String user_id){
        adminService.updateUser(user_id);
        return "redirect:/admin/u_index";
    }

    //미승인 판매자 리스트

    @GetMapping("/s_index")
    public String nSellerList(@RequestParam(value = "page", required = false, defaultValue = "0")Integer page, Model model){
       log.info("nseller");
       Pagenation pagenation = new Pagenation(page,10,adminService.countNseller());
       List<SellerDTO> nsellers = adminService.nSellerList(pagenation);

       model.addAttribute("nsellers",nsellers);
       model.addAttribute("page",pagenation);
       return "admin/s_index";
    }


    //승인 판매자 리스트
    //@MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("/ss_index")
    public String ySellerList(@RequestParam(value = "page", required = false, defaultValue = "0")Integer page, Model model){
       Pagenation pagenation = new Pagenation(page, 10, adminService.countYseller());
        List<SellerDTO> sellers = adminService.ySellerList(pagenation);
        model.addAttribute("sellers", sellers);
        model.addAttribute("page", pagenation);
        return "admin/ss_index";
    }


    //판매자 승인
    // @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("upSel/{user_id}")
    public String appro(@PathVariable String user_id){
        adminService.approSeller(user_id);
        return "redirect:/admin/s_index";
    }

    //판매자 승인 취소
    // @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("cancelSel/{user_id}")
    public String cancel(@PathVariable String user_id){
        adminService.cancelSeller(user_id);
        return "redirect:/admin/ss_index";
    }


    //레시피 목록
    @GetMapping("/r_index")
    //다현님꺼
    public ModelAndView rcpList(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                      @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                      @RequestParam(value="page", required=false, defaultValue = "1") int page, ModelAndView mav){

        // 전체 레코드 수
        Pagenation pagenation = new Pagenation(page, adminService.countrcp(cate_seq) , true);


        // 전체 레시피 목록
        List<RecipeDTO> rcpList = service.listProcess(pagenation, rcp_sort, cate_seq);

        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("cate_seq", cate_seq);
        mav.addObject("rcpList", rcpList);
        mav.addObject("pagenation", pagenation);
        mav.setViewName("admin/r_index");
        return mav;
    }

    //레시피글 누르면 상세내용 페이지 ->???????
    @GetMapping("/recipe/view/{rcp_seq}")
    public String rcpdetail(String rcp_name, Model model){
        // model.addAttribute("RecipeDTO", );
        return "redirect:/recipe/rcpView";
    }

    //레시피 삭제
    // @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("delrcp/{rcp_seq}")
    public  String delRcp(@PathVariable("rcp_seq") int rcp_seq){
        adminService.delRcp(rcp_seq);
        return "redirect:/admin/r_index";
    }

    //게시판 신고순 리스트
    @GetMapping("/c_index")
    public String appcomm(@RequestParam(value = "page", required = false, defaultValue = "0")Integer page,Model model){
        Pagenation pagenation = new Pagenation(page, 10, communityService.count());
        List<CommunityDTO> reportCom = adminService.reportCom(pagenation);
        model.addAttribute("reportCom", reportCom);
        model.addAttribute("page", pagenation);
        return "admin/c_index";
    }

    //게시판 삭제(완)
    // @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("/delcom/{id}")
    public String delcomm(@PathVariable Integer id){
        log.info("access");
        adminService.delcomm(id);
        //model.addAttribute("delcomm", delcomm);
        return "redirect:/admin/c_index";

    }


}
