package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ManualDTO {
    private Integer manual_no;
    private Integer rcp_seq;
    private String manual_txt;
    private String filename;
    private String filepath;
}
