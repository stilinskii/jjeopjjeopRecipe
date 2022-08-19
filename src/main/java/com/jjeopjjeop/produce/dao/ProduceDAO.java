package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import java.util.List;

import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProduceDAO {
    public void write(ProduceDTO produceDTO); //판매글 쓰기

    public List<ProduceDTO> produceList(RecipePageDTO recipePageDTO); //판매글 목록보기

    public List<ProduceDTO> produceListType(int type);//필터링한 판매글 목록보기 ->나중에 이거 지우고 타임리프로 처리하게 바꾸기

    public void produceDelete(int produce_num); //판매글 삭제하기

    public ProduceDTO produceView(int produce_num); //판매글 상세보기

    public void produceUpdate(ProduceDTO produceDto); //판매글 수정하기

    public int produceCount(); //페이지처리를 위한 판매글 개수세기

}
