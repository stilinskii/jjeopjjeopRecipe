package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
@NoArgsConstructor
public class CommunityBoardDTO {
    private String user_id;
    private Integer rep_seq;
    private String category;
    private String title;
    private String content;
    private String created_at;
    private String updated_at;
    private Integer read_count;
    private Integer like_count;
    private Integer report;


    public CommunityBoardDTO(String user_id, String category,String title, String content) {
        this.user_id = user_id;
        this.category = category;
        this.title = title;
        this.content = content;
    }
}
