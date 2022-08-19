package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.RecipeCommentDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.service.RecipeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RecipeCommentController {
    @Autowired
    private RecipeCommentService service;
    private int currentPage;

    // 덧글 조회
    @ResponseBody
    @GetMapping(value = "/recipe/comment/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipePageDTO> listMethod(String rcp_seq, String commentCurrentPage){
        // 덧글 처리
        int totalComment = service.countProcess(Integer.parseInt(rcp_seq));
        RecipePageDTO recipeCommentPageDTO = new RecipePageDTO();

        if(totalComment>0){
            int currentPage = Math.max(Integer.parseInt(commentCurrentPage), 1);
            recipeCommentPageDTO = new RecipePageDTO(currentPage, totalComment, Integer.parseInt(rcp_seq));
        }else{
            recipeCommentPageDTO = new RecipePageDTO(1, 0, Integer.parseInt(rcp_seq));
            recipeCommentPageDTO.setStartPage(1);
            recipeCommentPageDTO.setEndPage(1);
            recipeCommentPageDTO.setTotalPage(1);
        }
        recipeCommentPageDTO.setRecipeCommentDTOList(service.listProcess(recipeCommentPageDTO));

        return new ResponseEntity<>(recipeCommentPageDTO, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/recipe/comment/write")
    public void writeMethod(String rcp_seq, String comment_txt, HttpSession session, RecipeCommentDTO recipeCommentDTO){
        //System.out.println(comment_txt);
        recipeCommentDTO.setRcp_seq(Integer.parseInt(rcp_seq));
        recipeCommentDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        recipeCommentDTO.setComment_txt(comment_txt);
        service.writeProcess(recipeCommentDTO);
    }

    @ResponseBody
    @PostMapping("/recipe/comment/update")
    public void updateMethod(String co_rcp_seq, String comment_txt, RecipeCommentDTO recipeCommentDTO){
        recipeCommentDTO.setCo_rcp_seq(Integer.parseInt(co_rcp_seq));
        recipeCommentDTO.setComment_txt(comment_txt);
        service.updateProcess(recipeCommentDTO);
    }

    @ResponseBody
    @PostMapping("/recipe/comment/delete")
    public void deleteMethod(String co_rcp_seq){
        service.deleteProcess(Integer.parseInt(co_rcp_seq));
    }
}
