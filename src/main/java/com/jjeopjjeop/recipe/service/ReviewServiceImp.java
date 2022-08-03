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
    public List<ReviewDTO> reviewListProcess(String produce_name) {

        return reviewDAO.reviewList(produce_name);
    }



}
