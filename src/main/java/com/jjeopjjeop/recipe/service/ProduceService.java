package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProduceService {
    void writeProcess(ProduceDTO produceDTO, MultipartFile file) throws Exception;

    List<ProduceDTO> produceListProcess(RecipePageDTO recipePageDTO);

    List<ProduceDTO> produceListTypeProcess(int type);

    void produceDeleteProcess(int produce_num);

    ProduceDTO produceViewProcess(int produce_num);

    void produceUpdateProcess(ProduceDTO produceDTO);

    int countProcess(); //페이지 처리를 위해 판매글 개수 세기

    //하영
    List<ProduceDTO> findProductsByKeyword(String keyword);
    List<ProduceDTO> findProductsByKeywordWithPaging(String keyword, RecipePageDTO pageDTO);
}