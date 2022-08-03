package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.PageDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> reviewListProcess(String produce_name);
}
