package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.RecipeCommentDAO;
import com.jjeopjjeop.recipe.dto.RecipeCommentDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecipeCommentServiceImp implements RecipeCommentService{

    @Autowired
    private RecipeCommentDAO dao;

    public RecipeCommentServiceImp() {
    }

    @Override
    public int countProcess(int rcp_seq) {
        return dao.count(rcp_seq);
    }

    @Override
    public List<RecipeCommentDTO> listProcess(Pagenation pagenation, int rcp_seq) {

        return dao.list(Map.of("page", pagenation, "rcp_seq", rcp_seq));
    }

    @Override
    public void writeProcess(RecipeCommentDTO recipeCommentDTO) {
        dao.write(recipeCommentDTO);
    }

    @Override
    public void updateProcess(RecipeCommentDTO recipeCommentDTO){
        dao.update(recipeCommentDTO);
    }

    @Override
    public void deleteProcess(int co_rcp_seq) {
        dao.delete(co_rcp_seq);
    }
}
