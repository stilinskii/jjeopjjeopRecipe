package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "별점을 선택해주세요")
    private Float rating;

    @NotNull
    @Size(min = 1, max = 200, message = "내용을 1byte~200byte의 범위로 적어주세요")
    private String content;

    private Date created_date;
    //sql.date로 import하기.

}
