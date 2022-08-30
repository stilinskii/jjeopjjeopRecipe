package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.ReviewDTO;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    List<ReviewDTO> reviewList(Map<String, Object> map);//리뷰 리스트
    int reviewCount(int produce_num); //페이지 처리를 위한 각 판매글에 대한 리뷰 개수
    void reviewDelete(int produce_num); //리뷰삭제
    void reviewWrite(ReviewDTO reviewDTO); //리뷰 작성
    ReviewDTO reviewView(int pay_num); //리뷰 보기
    void reviewUpdate(ReviewDTO reviewDTO); //리뷰 작성 반영
}
