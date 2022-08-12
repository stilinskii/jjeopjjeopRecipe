package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.RecipeDTO;

import java.util.List;

public interface RecipeAPIService {
    public List<RecipeDTO> list(int num);
    public RecipeDTO view(int num);
}
