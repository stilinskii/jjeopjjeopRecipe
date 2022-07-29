package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.CategoryDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RecipeController {
    @Autowired
    private RecipeService service;
    private int currentPage;

    // 레시피 목록 조회 메소드
    @GetMapping("/recipe/list")
    public ModelAndView rcpListMethod(ModelAndView mav, RecipePageDTO pDto){
        // 전체 레코드 수
        int totalRecord = service.countProcess();
        //System.out.println(totalRecord);

        if(totalRecord>0){
            if(pDto.getCurrentPage()==0){
                currentPage = 1;
            }else{
                currentPage = pDto.getCurrentPage();
            }
            //System.out.println(currentPage);
            pDto = new RecipePageDTO(currentPage, totalRecord);
        }

        // 오늘의 인기 레시피 목록
        List<RecipeDTO> favoriteRcpList = service.favoriteListProcess();

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        // 전체 레시피 목록
        List<RecipeDTO> rcpList = service.listProcess(pDto);
        //System.out.println(rcpList);

        mav.addObject("totalRecord", totalRecord);
        mav.addObject("cateList", cateList);
        mav.addObject("favoriteRcpList", favoriteRcpList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("pDto", pDto);
        mav.setViewName("/recipe/rcpList");
        return mav;
    }

    // 레시피 목록 검색 메소드
    @GetMapping("/recipe/search")
    public String rcpSearchMethod(){

        return "/recipe/rcpSearch";
    }

    // 레시피 본문 조회 메소드
    @GetMapping("/recipe/view/{rcp_seq}")
    public ModelAndView rcpViewMethod(@PathVariable("rcp_seq") Integer rcp_seq, ModelAndView mav){

        mav.addObject("rcp", service.contentProcess(rcp_seq));
        mav.setViewName("/recipe/rcpView");
        return mav;
    }

    // 레시피 스크랩 메소드
    @PutMapping("/recipe/scrap")
    public String rcpScrapMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 신고 메소드
    @PutMapping("/recipe/report")
    public String rcpReportMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 작성 페이지 요청 메소드
    @GetMapping("/recipe/write")
    public String rcpWriteMethod(){

        return "/recipe/rcpWrite";
    }

    // 레시피 작성 메소드
    @PostMapping("/recipe/write")
    public String rcpWriteProMethod(RecipeDTO dto){
        service.writeProcess(dto);
        // validation!!

        return "redirect:/recipe/list";
    }

    // 레시피 수정 페이지 요청 메소드
    @GetMapping("/recipe/update")
    public String rcpUpdateMethod(){

        return "/recipe/rcpUpdate";
    }

    // 레시피 수정 메소드
//    public String rcpUpdateMethod(){
//
//        return null;
//    }

    // 레시피 삭제 메소드
    @DeleteMapping("/recipe/delete")
    public String rcpDeleteMethod(){

        return "redirect:/recipe/list";
    }

    //////////// 레시피 댓글 관리 ////////////
    // 레시피 댓글 작성 메소드
    @PostMapping("/recipe/comment")
    public String rcpCoWriteMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 댓글 수정 메소드
    @PutMapping("/recipe/comment/update")
    public String rcpCoUpdateMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 댓글 삭제 메소드
    @DeleteMapping("/recipe/comment/delete")
    public String rcpCoDeleteMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 댓글 신고 메소드
    @PutMapping("/recipe/comment/report")
    public String rcpCoReportMethod(){

        return "redirect:/recipe/view";
    }
}
