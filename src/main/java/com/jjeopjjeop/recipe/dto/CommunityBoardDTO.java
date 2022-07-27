package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CommunityBoardDTO {
    private String user_id;
    private Integer rep_seq;
    private String title;
    private String content;
    private Integer read_count;
    private String created_at;
    private String updated_at;
    private Integer report;
}
