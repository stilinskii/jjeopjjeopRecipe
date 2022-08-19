package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;


@Component
@Data
public class ReviewDTO {

/*
pay_num	NUMBER		NOT NULL,
produce_name	VARCHAR2(30)		NOT NULL,
rating	NUMBER		NOT NULL,
content	VARCHAR2(200)		NOT NULL,
created_date	DATE		NOT NULL
 */

    private Integer pay_num;
    private String produce_name;
    private Float rating;
    private String content;
    private Date created_date;
    //sql.date로 import하기.

}
