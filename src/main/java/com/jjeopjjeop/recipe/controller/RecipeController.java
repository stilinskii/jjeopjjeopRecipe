package com.jjeopjjeop.recipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {

    // 레시피 목록 조회 메소드
    @GetMapping("/recipe/list")
    public String rcpListMethod(){

        return "/recipe/rcpList";
    }

    // 레시피 목록 검색 메소드
    @GetMapping("/recipe/search")
    public String rcpSearchMethod(){

        return "/recipe/rcpSearch";
    }

    // 레시피 본문 조회 메소드
    @GetMapping("/recipe/view")
    public String rcpViewMethod(){

        return "/recipe/rcpView";
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
//    public String rcpWriteMethod(){
//
//        return null;
//    }

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
