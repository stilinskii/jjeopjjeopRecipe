package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CategoryDTO {
    private Integer cate_seq;
    private String cate_name;
    private Integer cate_cate;

    // 데이터 삽입용
    private Integer rcp_seq;
}
