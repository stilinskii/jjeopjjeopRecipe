package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Community {
    private String user_id;
    private Integer rep_seq;
    private String category;
    @NotNull
    @Size(min=2,max=30)
    private String title;
    @Size(min=2)
    private String content;
    private String created_at;
    private String updated_at;
    private Integer read_count;
    private Integer like_count;
    private Integer report;


    public Community(String user_id, String category, String title, String content) {
        this.user_id = user_id;
        this.category = category;
        this.title = title;
        this.content = content;
    }
}
