package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ProduceDTO {
    private Integer produce_num;
    private String user_id;
    private Integer produce_type;
    private String produce_name;
    private Integer price;
    private String produce_image;
    private String produce_image_path;
    private String produce_description;

    @Autowired
    private PayDTO payDTO;//장바구니에 쓰려고 만듦.
    //여기서 Autowired안하면 다른데서 아래처럼쓸때
    //produceDTO.getPayDTO().setPay_num(1);
    // java.lang.NullPointerException: null 이거 나옴.

    @Autowired
    private List<PayDTO> payDTOList;
}