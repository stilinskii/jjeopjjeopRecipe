package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.*;
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
    public void write(RecipeDTO dto);
    public void writeManual(ManualDTO mDto);
    public RecipeDTO updateNum(int num);
    public void update(RecipeDTO dto);
    public void delete(int num);
    public void deleteManual(int num);
    public String getFile(int num); // 파일 다운에 사용
}
