package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Component
public class RecipeDTO {
    private int rcp_seq;
    private int scrap;
    private int report;
    private int rcp_viewcnt;
    private String rcp_name;
    private String user_id = "abc";
    private String filename;
    private String filepath;
    private String time;
    private String difficulty;
    private String amount;
    private String summary;
    private String hash_tag;
    private String rcp_parts_dtls;
    private String rcp_date;

    // db에 없는 것
    private MultipartFile upload; // 첨부파일 처리
    private boolean scrapOrNot; // 스크랩 처리
    private boolean reportOrNot; // 신고 처리

    //api 테스트용
    private List<ManualDTO> manualDTOList;
}