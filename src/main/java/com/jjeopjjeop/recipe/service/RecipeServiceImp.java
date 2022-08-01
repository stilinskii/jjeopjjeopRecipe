package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.RecipeDAO;
import com.jjeopjjeop.recipe.dto.CategoryDTO;
import com.jjeopjjeop.recipe.dto.ManualDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
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
    public int countProcess() {
        return dao.count();
    }

    @Override
    public List<RecipeDTO> listProcess(RecipePageDTO pDto) {
        return dao.list(pDto);
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
    public void writeProcess(RecipeDTO dto) {

        dao.write(dto);
    }

    public void writeMProcess(ManualDTO mDto){
        dao.writeManual(mDto);
    }
}
