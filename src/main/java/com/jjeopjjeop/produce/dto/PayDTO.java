package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PayDTO {
    /*
    pay_num	NUMBER		NOT NULL,
    user_id	VARCHAR2(10)		NOT NULL,
    produce_num	NUMBER		NOT NULL,
    quantity	NUMBER		NOT NULL,
    pay	NUMBER	DEFAULT 0	NOT NULL,
    total_price	NUMBER		NOT NULL
     */
    private Integer pay_num;
    private String user_id;
    private Integer produce_num;
    private Integer quantity;
    private Integer pay;
    private Integer total_price;
    private Integer review_check;
}
