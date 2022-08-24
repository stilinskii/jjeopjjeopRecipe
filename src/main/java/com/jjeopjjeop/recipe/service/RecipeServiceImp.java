package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.RecipeDAO;
import com.jjeopjjeop.recipe.dto.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class RecipeServiceImp implements RecipeService{
    @Autowired
    private RecipeDAO dao;

    public RecipeServiceImp() {

    }

    @Override
    public int countProcess(int cate_seq) {
        return dao.count(cate_seq);
    }

    @Override
    public List<RecipeDTO> listProcess(RecipePageDTO recipePageDTO) {
        return dao.list(recipePageDTO);
    }

    @Override
    public int searchCountProcess(RecipePageDTO recipePageDTO) {
        return dao.searchCount(recipePageDTO);
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

    public int chkScrapProcess(UserScrapDTO userScrapDTO){
        return dao.checkScrap(userScrapDTO);
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
    public int chkReportProcess(ReportRecipeDTO reportRecipeDTO) {
        return dao.checkReport(reportRecipeDTO);
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
    public void writeCProcess(int cate_seq) {
        dao.writeCate(cate_seq);
    }

    @Override
    public List<Integer> updatePageProcess(int rcp_seq) {
        return dao.updateCate(rcp_seq);
    }

    @Override
    public void deleteProcess(int rcp_seq, String url1, String url2) {
        // 대표 이미지 있으면 삭제
        String path = dao.getFile(rcp_seq);
        if(path != null){
            File fe = new File(url1, path);
            fe.delete();
        }

        // 요리과정 이미지 있으면 삭제
        List<String> list = dao.getFileM(rcp_seq);
        for(int i=0; i<list.size(); i++){
            String pathM = list.get(i);
            if(pathM != null){
                File fm = new File(url2, pathM);
                fm.delete();
            }
        }

        dao.delete(rcp_seq);
    }

    //heyeong
    @Override
    public List<RecipeDTO> searchListByKeyword(String searchKey) {
        return dao.searchListByKeyword(searchKey);
    }
}
