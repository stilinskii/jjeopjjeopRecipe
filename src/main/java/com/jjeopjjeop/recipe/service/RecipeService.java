package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;

import java.util.List;

public interface RecipeService {
    public int countProcess(int cate_seq);
    public List<RecipeDTO> listProcess(Pagenation pagenation, int rcp_sort, int cate_seq);
    public int searchCountProcess(String searchKey, int cate_seq);
    public List<RecipeDTO> searchListProcess(Pagenation pagenation, int rcp_sort, int cate_seq, String searchKey);
    public List<RecipeDTO> favoriteListProcess();
    public List<CategoryDTO> cateListProcess();
    public RecipeDTO contentProcess(int rcp_seq);
    public List<ManualDTO> contentMnlProcess(int rcp_seq);
    public int chkScrapProcess(UserScrapDTO userScrapDTO);
    public void scrapProcess(UserScrapDTO userScrapDTO);
    public int chkReportProcess(ReportRecipeDTO reportRecipeDTO);
    public void reportProcess(ReportRecipeDTO reportRecipeDTO);
    public void writeProcess(RecipeDTO recipeDTO);
    public void writeMProcess(ManualDTO manualDTO);
    public void writeCProcess(int cate_seq);
    public List<Integer> updatePageProcess(int rcp_seq);
    public void updateProcess(RecipeDTO recipeDTO, String url, boolean isChange);
    public void updateMProcess(ManualDTO manualDTO, String url);
    public void updateCProcess(int cate_seq, int rcp_seq);
    public void deleteCProcess(int rcp_seq);
    public void deleteProcess(int rcp_seq, String url1, String url2);

    //hayeong
    List<RecipeDTO> searchListByKeyword(String searchKey);
}
