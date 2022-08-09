package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.RecipeDAO;
import com.jjeopjjeop.recipe.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImp implements RecipeService{
    @Autowired
    private RecipeDAO dao;

    public RecipeServiceImp() {

    }

    @Override
    public int countProcess(int[] cate_seq) {
        return dao.count(cate_seq);
    }

    @Override
    public List<RecipeDTO> listProcess(RecipePageDTO recipePageDTO) {
        return dao.list(recipePageDTO);
    }

    @Override
    public int searchCountProcess(String searchKey) {
        return dao.searchCount(searchKey);
    }

    @Override
    public List<RecipeDTO> searchListProcess(RecipePageDTO recipePageDTO) {
        return dao.searchList(recipePageDTO);
    }

    @Override
    public List<RecipeDTO> favoriteListProcess() {
        return dao.favoriteList();
    }

    public List<CategoryDTO> cateListProcess(){
        return dao.cateList();
    }

    @Override
    public RecipeDTO contentProcess(int rcp_seq) {
        dao.viewCnt(rcp_seq);
        return dao.content(rcp_seq);
    }

    @Override
    public List<ManualDTO> contentMnlProcess(int rcp_seq) {
        return dao.contentMnl(rcp_seq);
    }

    @Override
    public void scrapProcess(UserScrapDTO userScrapDTO) {
        if(dao.checkScrap(userScrapDTO) == 0){
            dao.scrapP(userScrapDTO);
            dao.updateScrapP(userScrapDTO.getRcp_seq());
        }else{
            dao.scrapM(userScrapDTO);
            dao.updateScrapM(userScrapDTO.getRcp_seq());
        }
    }

    @Override
    public void reportProcess(ReportRecipeDTO reportRecipeDTO) {
        if(dao.checkReport(reportRecipeDTO) == 0){
            dao.report(reportRecipeDTO);
            dao.updateReport(reportRecipeDTO.getRcp_seq());
        }
    }

    @Override
    public void writeProcess(RecipeDTO recipeDTO) {
        dao.write(recipeDTO);
    }

    public void writeMProcess(ManualDTO manualDTO){
        dao.writeManual(manualDTO);
    }

    @Override
    public void deleteProcess(int rcp_seq) {
        dao.deleteManual(rcp_seq);
        dao.delete(rcp_seq);
    }
}
