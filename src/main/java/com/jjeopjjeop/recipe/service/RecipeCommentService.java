package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.RecipeCommentDAO;
import com.jjeopjjeop.recipe.dto.RecipeCommentDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;

import java.util.List;

public interface RecipeCommentService {
    public int countProcess(int rcp_seq);
    public List<RecipeCommentDTO> listProcess(Pagenation pagenation, int rcp_seq);
    public void writeProcess(RecipeCommentDTO recipeCommentDTO);
    public void updateProcess(RecipeCommentDTO recipeCommentDTO);
    public void deleteProcess(int co_rcp_seq);
}
