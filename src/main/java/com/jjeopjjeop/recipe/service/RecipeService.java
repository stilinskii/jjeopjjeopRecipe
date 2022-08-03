package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.*;

import java.util.List;

public interface RecipeService {
    public int countProcess();
    public List<RecipeDTO> listProcess(RecipePageDTO recipePageDTO);
    public List<RecipeDTO> favoriteListProcess();
    public List<CategoryDTO> cateListProcess();
    public RecipeDTO contentProcess(int rcp_seq);
    public List<ManualDTO> contentMnlProcess(int rcp_seq);
    public void scrapProcess(UserScrapDTO userScrapDTO);
    public void writeProcess(RecipeDTO recipeDTO);
    public void writeMProcess(ManualDTO manualDTO);
    public void deleteProcess(int rcp_seq);
}
