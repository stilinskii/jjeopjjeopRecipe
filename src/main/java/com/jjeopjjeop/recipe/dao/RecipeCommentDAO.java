package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.RecipeCommentDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RecipeCommentDAO {
    public int count(int rcp_seq);
    public List<RecipeCommentDTO> list(Map<String, Object> map);
    public void write(RecipeCommentDTO recipeCommentDTO);
    public void update(RecipeCommentDTO recipeCommentDTO);
    public void delete(int co_rcp_seq);
}
