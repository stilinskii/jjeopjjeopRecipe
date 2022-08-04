package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Component
public class RecipeDTO {
    private int rcp_seq;
    private int scrap;
    private int report;
    private int rcp_viewcnt;
    private String rcp_name;
    private String user_id = "테스트";
    private String filename;
    private String filepath;
    private String time;
    private String difficulty;
    private String amount;
    private String summary;
    private String hash_tag;
    private String rcp_parts_dtls;
    private String rcp_date;
    private MultipartFile upload;
}