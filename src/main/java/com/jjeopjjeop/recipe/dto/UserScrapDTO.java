package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserScrapDTO {
    private String user_id;
    private int rcp_seq;
    private String scr_date;
}
