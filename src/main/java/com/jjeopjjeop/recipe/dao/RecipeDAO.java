package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RecipeDAO {
    // 리스트
    public int count(int cate_seq);
    public List<RecipeDTO> list(Map<String, Object> map);
    public List<RecipeDTO> favoriteList();
    public List<CategoryDTO> cateList();

    // 검색 리스트
    public int searchCount(Map<String, Object> map);
    public List<RecipeDTO> searchList(Map<String, Object> map);

    // 본문
    public List<CategoryDTO> getRcpCate(int rcp_seq);
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

    // write 처리
    public void write(RecipeDTO recipeDTO);
    public void writeManual(ManualDTO manualDTO);
    public void writeCate(int cate_seq);

    // update 처리
    public void update(RecipeDTO recipeDTO);
    public void updateManual(ManualDTO manualDTO);
    public void updateCate(@Param("cate_seq") int cate_seq, @Param("rcp_seq") int rcp_seq);

    // 삭제
    public void delete(int num);
    public void deleteManual(int num);
    public void deleteCate(int rcp_seq);

    // 첨부파일 처리
    public String getFile(int rcp_seq); // 레시피 대표 첨부파일
    public List<String> getFileM(int rcp_seq); // 요리과정 첨부파일

    //hayeong
    List<RecipeDTO> searchListByKeyword(String searchKey);

}
