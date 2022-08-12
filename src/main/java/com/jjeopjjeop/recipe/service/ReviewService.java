package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    public List<ReviewDTO> reviewListProcess(int produce_num);//리뷰 리스트
    public void reviewDelete(int produce_num); //리뷰삭제
}
