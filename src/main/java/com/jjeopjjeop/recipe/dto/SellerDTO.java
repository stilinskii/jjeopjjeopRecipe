package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SellerDTO {
    private String user_id;
    private String business_name;
    private Integer registration_number;
    private Integer seller_phone;
    private String seller_address;
    private Integer approval;


}
