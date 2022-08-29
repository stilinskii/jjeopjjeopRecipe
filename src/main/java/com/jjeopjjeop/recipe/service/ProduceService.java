package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProduceService {
    void writeProcess(ProduceDTO produceDTO, MultipartFile file) throws Exception;

    List<ProduceDTO> produceList(Map<String, Object> map);

    List<ProduceDTO> produceListSort(Map<String, Object> map); //판매글 리스트 정렬

    void produceDeleteProcess(int produce_num);

    ProduceDTO produceViewProcess(int produce_num);

    void produceUpdateProcess(ProduceDTO produceDTO);

    int produceFilterCount(int produce_type); //페이지 처리를 위해 판매글 개수 세기
    int produceSortCount(int sort); //페이지처리를 위한 판매글(정렬) 개수세기

    //하영
    List<ProduceDTO> findProduceByKeyword(String keyword);
    List<ProduceDTO> findProduceByKeywordWithPaging(String keyword, Pagenation pagenation);

    List<ProduceDTO> getPopularProduceList();
}