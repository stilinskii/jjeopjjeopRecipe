package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.PageDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {

    public List<ReviewDTO> reviewListProcess(int produce_num);//리뷰 리스트
    public void reviewDelete(int produce_num); //리뷰삭제
}
