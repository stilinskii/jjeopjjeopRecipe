package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CategoryDTO {
    private Integer cate_seq;
    private String cate_name;
    private Integer cate_cate;
    private boolean rcp_chk = false; // 레시피 수정 폼 용
}
