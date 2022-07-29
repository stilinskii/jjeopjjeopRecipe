package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RecipeDTO {
    private int rcp_seq, scrap, report, rcp_viewcnt;
    private String rcp_name, user_id, filename, filepath, time,
            difficulty, amount, summary, hash_tag, rcp_parts_dtls;
    private String rcp_date;

    public RecipeDTO() {
    }
}