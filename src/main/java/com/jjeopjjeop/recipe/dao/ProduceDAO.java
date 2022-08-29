package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.ProduceDTO;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProduceDAO {
    void write(ProduceDTO produceDTO); //판매글 쓰기

    List<ProduceDTO> produceList(Map<String, Object> map); //판매글 리스트 필터링

    List<ProduceDTO> produceListSort(Map<String, Object> map); //판매글 리스트 정렬
    
    void produceUpdateSale(int produce_num); //판매중지로 변경

    ProduceDTO produceView(int produce_num); //판매글 상세보기

    void produceUpdate(ProduceDTO produceDto); //판매글 수정하기

    int produceFilterCount(int produce_type); //페이지처리를 위한 판매글(필터링) 개수세기
    
    int produceSortCount(int sort); //페이지처리를 위한 판매글(정렬) 개수세기

    String searchSellerBusinessName(String user_id); //판매자id로 상호명 검색.

    //하영 통합검색
    List<ProduceDTO> findProduceByKeyword(String keyword);

    List<ProduceDTO> findProduceByKeywordWithPaging(Map<String, Object> map);

    List<Integer> getFourProduceNumOrderBySoldCount();
}
