package com.jjeopjjeop.recipe.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

@Data
public class CommunitySearchForm {
    private String period;
    private String category;
    private String option;
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date from;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date to;


}
