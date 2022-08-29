package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.RecipeCommentDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.RecipeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RecipeCommentController {
    @Autowired
    private RecipeCommentService service;

    // 댓글 조회
    @ResponseBody
    @GetMapping(value = "/recipe/comment/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> listMethod(String rcp_seq, String commentCurrentPage){
        // 덧글 처리
        Pagenation pagenation = new Pagenation(Integer.parseInt(commentCurrentPage), service.countProcess(Integer.parseInt(rcp_seq)), false);

        Map<String, Object> map = new HashMap<>();
        map.put("list", service.listProcess(pagenation, Integer.parseInt(rcp_seq)));
        map.put("page", pagenation);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 댓글 작성
    @ResponseBody
    @PostMapping("/recipe/comment/write")
    public void writeMethod(String rcp_seq, String comment_txt, HttpSession session, RecipeCommentDTO recipeCommentDTO){
        recipeCommentDTO.setRcp_seq(Integer.parseInt(rcp_seq));
        recipeCommentDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        recipeCommentDTO.setComment_txt(comment_txt);
        service.writeProcess(recipeCommentDTO);
    }

    // 댓글 수정
    @ResponseBody
    @PostMapping("/recipe/comment/update")
    public void updateMethod(String co_rcp_seq, String comment_txt, RecipeCommentDTO recipeCommentDTO){
        recipeCommentDTO.setCo_rcp_seq(Integer.parseInt(co_rcp_seq));
        recipeCommentDTO.setComment_txt(comment_txt);
        service.updateProcess(recipeCommentDTO);
    }

    // 댓글 삭제
    @ResponseBody
    @PostMapping("/recipe/comment/delete")
    public void deleteMethod(String co_rcp_seq){
        service.deleteProcess(Integer.parseInt(co_rcp_seq));
    }
}
