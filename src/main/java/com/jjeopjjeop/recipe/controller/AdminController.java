package com.jjeopjjeop.recipe.controller;



import com.jjeopjjeop.recipe.config.MySecured;
import com.jjeopjjeop.recipe.dto.*;

import com.jjeopjjeop.recipe.dto.A_userDTO;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.SellerDTO;

import com.jjeopjjeop.recipe.pagenation.Pagenation;
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
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final CommunityService communityService;
    private final RecipeService service;

    private int currentPage;

    //관리자 페이지 실행
    @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("/main")
    public String admin(){
        return "admin/main";
    }


    //회원목록 불러오기(recipepageDTO)
//    @RequestMapping(value= "/u_index", method = RequestMethod.GET)
//    public ModelAndView listUser(ModelAndView mav, HttpServletRequest request, RecipePageDTO recipePageDTO) throws Exception {
//        int totalUSer = adminService.countUser();
//        if(totalUSer>0){
//            currentPage = Math.max(recipePageDTO.getCurrentPage(),1);
//            recipePageDTO = new RecipePageDTO(currentPage, totalUSer);
//        }
//
//        List<UserDTO> users = adminService.UserList(recipePageDTO);
////        mav.addObject("listUser", adminService.UserList(recipePageDTO));
////        System.out.println("===================================");
////        System.out.println(recipePageDTO);
////        System.out.println("===================================");
//        mav.addObject("totalUser", totalUSer);
//        mav.addObject("pdto", recipePageDTO);
//        mav.addObject("users", users);
//        mav.setViewName("/admin/u_index");
//        return mav;
//    }

    //회원목록(recipepageDTO)
//    @GetMapping("/u_index")
//      public String UserList(Model model, RecipePageDTO recipePageDTO) {
//        log.info("회원 리스트");
//        int totalUser = adminService.countUser();
//
//        if(totalUser>0){
//            currentPage = Math.max(recipePageDTO.getCurrentPage(),1);
//            recipePageDTO = new RecipePageDTO(currentPage, totalUser);
//        }
//
////        List<UserDTO> users = adminService.USerList(pv);
////         model.addAttribute("users", users);
//        model.addAttribute("count", totalUser);
//        model.addAttribute("users", adminService.UserList(recipePageDTO));
//        model.addAttribute("pdto", recipePageDTO);
//
//          return "admin/u_index";
//    }

    //회원목록 (cri)
//    @MySecured(role = MySecured.Role.ADMIN)
//    @GetMapping("/u_index")
//    public String UserList(Model model, A_criteria cri) {
//        log.info("회원 리스트");
//        //int totalUSer = adminService.countUser();
////        List<UserDTO> users = adminService.USerList(pv);
////         model.addAttribute("users", users);
//        model.addAttribute("count", adminService.countUser());
//        model.addAttribute("users", adminService.UserList(cri));
//        model.addAttribute("pageMaker", new A_pageDTO(cri, adminService.countUser(), 10));
//        return "admin/u_index";
//    }

    //page
   // @MySecured(role = MySecured.Role.ADMIN)
    @GetMapping("/u_index")
    public String UserList(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model) {
        log.info("회원 리스트");
       Pagenation pagenation = new Pagenation(page, 10, adminService.countUser());
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

    //회원 삭제하기
    @GetMapping("delU/{user_id}")
    public String delUser(@PathVariable String user_id){
        adminService.delUser(user_id);
        return "redirect:/admin/u_index";
    }

    //미승인 판매자 리스트
    //@MySecured(role = MySecured.Role.ADMIN)
//    @GetMapping("/s_index")
//    public String nSellerList(A_criteria cri, Model model){
//        log.info("nseller");
//        model.addAttribute("nsellers", adminService.nSellerList(cri));
//        model.addAttribute("ncount", adminService.countNseller());
//        model.addAttribute("pageMaker", new A_pageDTO(cri, adminService.countNseller(), 10));
//        return "admin/s_index";
//    }
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
        List<SellerDTO> sellers = adminService.nSellerList(pagenation);
        model.addAttribute("sellers", sellers);
        //model.addAttribute("ycount", adminService.countYseller());
        model.addAttribute("page", pagenation);
        return "admin/ss_index";
    }


    //판매자 승인
    @GetMapping("upSel/{user_id}")
    public String appro(@PathVariable String user_id){
        adminService.approSeller(user_id);
        return "redirect:/admin/s_index";
    }

    //판매자 승인 취소
    @GetMapping("cancelSel/{user_id}")
    public String cancel(@PathVariable String user_id){
        adminService.cancelSeller(user_id);
        return "redirect:/admin/ss_index";
    }


    //레시피 목록
    @GetMapping("/r_index")
    //다현님꺼 수정해보기
    public String rcpList(@RequestParam(value="page", required=false, defaultValue = "0") Integer page,
                          @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq, Model model){

        // 전체 레코드 수
        Pagenation pagenation = new Pagenation(page,6 ,adminService.countrcp(cate_seq));
        // 전체 레시피 목록
        List<RecipeDTO> rcpList = adminService.rcpList(pagenation);

        model.addAttribute("cate_seq", cate_seq);
        model.addAttribute("rcpList", rcpList);
        model.addAttribute("pagenation", pagenation);
        return "admin/r_index";
    }
//    public ModelAndView rcpList(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
//                                      @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
//                                      @RequestParam(value="page", required=false, defaultValue = "1") int page, ModelAndView mav){
//
//        // 전체 레코드 수
//        Pagenation pagenation = new Pagenation(page, service.countProcess(cate_seq), true);
//        // 전체 레시피 목록
//       // List<RecipeDTO> rcpList = service.listProcess(pagenation, rcp_sort, cate_seq);
//        List<RecipeDTO> rcpList = adminService.rcpList(pagenation, rcp_sort, cate_seq);
//
//        mav.addObject("rcp_sort", rcp_sort);
//        mav.addObject("cate_seq", cate_seq);
//        mav.addObject("cateList", cateList);
//        mav.addObject("favoriteRcpList", favoriteRcpList);
//        mav.addObject("rcpList", rcpList);
//        mav.addObject("pagenation", pagenation);
//        mav.setViewName("/recipe/rcpList");
//        return mav;
//    }

    //레시피글 누르면 상세내용 페이지 ->???????
    @GetMapping("/recipe/view/{rcp_seq}")
    public String rcpdetail(String rcp_name, Model model){
        // model.addAttribute("RecipeDTO", );
        return "redirect:/recipe/rcpView";
    }

//    private RecipeDTO getRecipe(){
//        return
//    }

    //레시피 게시글 삭제

    //레시피 삭제
    @GetMapping("delrcp/{user_id}")
    public  String delRcp(@PathVariable String user_id){
        adminService.delRcp(user_id);
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

    //게시판 상세 페이지


    //게시판 삭제 성공
    @GetMapping("/delcom/{id}")
    public String delcomm(@PathVariable Integer id){
        log.info("access");
        adminService.delcomm(id);
        //model.addAttribute("delcomm", delcomm);
        return "redirect:/admin/c_index";

    }


}
