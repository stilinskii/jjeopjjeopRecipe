package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReviewDAO {
    public List<ReviewDTO> reviewList(int produce_num); //리뷰 리스트

    public void reviewDelete(int produce_num); //리뷰 삭제
    
    public void reviewWrite(ReviewDTO reviewDTO); //리뷰 작성

    public ReviewDTO reviewView(int pay_num); //리뷰 보기
    
    public void reviewUpdate(ReviewDTO reviewDTO); //리뷰 작성 반영

}
