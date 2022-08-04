package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.RecipeCommentDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;

import java.util.List;

public interface RecipeCommentService {
    public int countProcess(int rcp_seq);
    public List<RecipeCommentDTO> listProcess(int rcp_seq);

    public void writeProcess(RecipeCommentDTO recipeCommentDTO);
    public void deleteProcess(int co_rcp_seq);
}
