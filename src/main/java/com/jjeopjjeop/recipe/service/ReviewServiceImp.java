package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.ReviewDAO;
import com.jjeopjjeop.recipe.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService{

    @Autowired
    private ReviewDAO reviewDAO;

    public ReviewServiceImp() {
    }

    @Override
    public List<ReviewDTO> reviewListProcess(int produce_num) {

        return reviewDAO.reviewList(produce_num);
    }

    //리뷰삭제
    @Override
    public void reviewDelete(int produce_num) {
        reviewDAO.reviewDelete(produce_num);
    }

    //리뷰 작성
    @Override
    public void reviewWrite(ReviewDTO reviewDTO) {
        reviewDAO.reviewWrite(reviewDTO);
    }


}
