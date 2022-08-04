package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

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
}