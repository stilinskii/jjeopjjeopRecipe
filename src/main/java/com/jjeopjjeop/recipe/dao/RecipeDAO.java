package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecipeDAO {
    public int count(int cate_seq);
    //public int count();
    public List<RecipeDTO> list(RecipePageDTO recipePageDTO);
    public int searchCount(String searchKey);
    public List<RecipeDTO> searchList(RecipePageDTO recipePageDTO);
    public List<RecipeDTO> favoriteList();
    public List<CategoryDTO> cateList();
    public RecipeDTO content(int num);
    public List<ManualDTO> contentMnl(int num);
    public void viewCnt(int num);

    // scrap 처리
    public int checkScrap(UserScrapDTO userScrapDTO); //중복인가?
    public void scrapP(UserScrapDTO userScrapDTO); //스크랩 등록
    public void updateScrapP(int num); //레시피 스크랩수 증가
    public void scrapM(UserScrapDTO userScrapDTO); //스크랩 해제
    public void updateScrapM(int num); //레시피 스크랩수 감소

    // report 처리
    public int checkReport(ReportRecipeDTO reportRecipeDTO); //중복인가?
    public void report(ReportRecipeDTO reportRecipeDTO); //신고
    public void updateReport(int num); //레시피 신고수 증가

//    public void reStepCount(RecipeDTO model);

    // write 처리
    public void write(RecipeDTO recipeDTO);
    public void writeManual(ManualDTO manualDTO);
    public void writeCate(int cate_seq);

    public RecipeDTO updateNum(int num);
    public void update(RecipeDTO recipeDTO);
    public void delete(int num);
    public void deleteManual(int num);
    public void deleteCate(int num);
    public String getFile(int num); // 파일 다운에 사용
}
