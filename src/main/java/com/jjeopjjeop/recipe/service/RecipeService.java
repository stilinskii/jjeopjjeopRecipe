package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.CategoryDTO;
import com.jjeopjjeop.recipe.dto.ManualDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;

import java.util.List;

public interface RecipeService {
    public int countProcess();
    public List<RecipeDTO> listProcess(RecipePageDTO pDto);
    public List<RecipeDTO> favoriteListProcess();
    public List<CategoryDTO> cateListProcess();
    public RecipeDTO contentProcess(int rcp_seq);
    public void writeProcess(RecipeDTO dto);
    public void writeMProcess(ManualDTO mDto);
}
