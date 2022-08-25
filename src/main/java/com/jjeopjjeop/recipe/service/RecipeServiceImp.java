package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.RecipeDAO;
import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

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
    public List<RecipeDTO> listProcess(Pagenation pagenation, int rcp_sort, int cate_seq) {
        return dao.list(Map.of("page",pagenation,"rcp_sort",rcp_sort,"cate_seq",cate_seq));
    }

    @Override
    public int searchCountProcess(String searchKey, int cate_seq) {
        return dao.searchCount(Map.of("searchKey", searchKey, "cate_seq", cate_seq));
    }

    @Override
    public List<RecipeDTO> searchListProcess(Pagenation pagenation, int rcp_sort, int cate_seq, String searchKey) {
        return dao.searchList(Map.of("page",pagenation,"rcp_sort",rcp_sort,"cate_seq",cate_seq, "searchKey", searchKey));
    }

    @Override
    public List<RecipeDTO> favoriteListProcess() {
        return dao.favoriteList();
    }

    public List<CategoryDTO> cateListProcess(){
        return dao.cateList();
    }

    public List<CategoryDTO> getRcpCateProcess(int rcp_seq){
        return dao.getRcpCate(rcp_seq);
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
    public void updateProcess(RecipeDTO recipeDTO, String url, boolean isChange){
        // 이미지 변경되었으면 이전 이미지 파일 삭제
        if(isChange){
            String path = dao.getFile(recipeDTO.getRcp_seq());
            if(path != null){
                File fe = new File(url, path);
                fe.delete();
            }
        }

        dao.update(recipeDTO);
    }

    @Override
    public void updateMProcess(ManualDTO manualDTO, String url){
        // 서버에 저장된 요리과정 이미지 삭제
        if(manualDTO.getManual_no() == 1){
            List<String> list = dao.getFileM(manualDTO.getRcp_seq());
            for(int i=0; i<list.size(); i++){
                String pathM = list.get(i);
                if(pathM != null){
                    File fm = new File(url, pathM);
                    fm.delete();
                }
            }
        }

        // 요리과정 전체 삭제 후 재등록
        if(manualDTO.getManual_no() == 1)
            dao.deleteManual(manualDTO.getRcp_seq());
        dao.updateManual(manualDTO);
    }

    @Override
    public void updateCateProcess(int cate_seq, int rcp_seq){
        dao.updateCate(cate_seq, rcp_seq);
    }

    public void deleteCProcess(int rcp_seq){
        dao.deleteCate(rcp_seq);
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
