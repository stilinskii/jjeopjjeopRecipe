package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@Component
public class RecipeCommentDTO {
    private Integer co_rcp_seq;
    private Integer rcp_seq;
    private String user_id;
    private String co_date;
    private Integer reports;
    private Integer re_co_seq;
    private Integer re_step;
    private String filename;
    private String filepath;
    private String comment_txt;

    private MultipartFile upload_comment;
}
