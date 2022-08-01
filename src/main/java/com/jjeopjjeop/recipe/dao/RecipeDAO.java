package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.CategoryDTO;
import com.jjeopjjeop.recipe.dto.ManualDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecipeDAO {
    public int count(); // 목록 숫자 세기
//    public List<RecipeDTO> list(RecipePageDTO pDto);
    public List<RecipeDTO> list(RecipePageDTO pDto);
    public List<RecipeDTO> favoriteList();
    public List<CategoryDTO> cateList();
    public RecipeDTO content(int num);
    public List<ManualDTO> contentMnl(int num);
    public void viewCnt(int num);
//    public void reStepCount(RecipeDTO model);
    public void write(RecipeDTO dto);
    public void writeManual(ManualDTO mDto);
    public RecipeDTO updateNum(int num);
    public void update(RecipeDTO dto);
    public void delete(int num);
    public String getFile(int num); // 파일 다운에 사용
}
