package com.jjeopjjeop.recipe.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@NoArgsConstructor
public class SellerDTO {
    private String user_id;
    private String business_name;
    private Integer registration_number;
    private Integer seller_phone;
    private String seller_address;
    private Integer approval;

    public SellerDTO(String user_id, String business_name, Integer registration_number, Integer seller_phone, String seller_address) {
        this.user_id = user_id;
        this.business_name = business_name;
        this.registration_number = registration_number;
        this.seller_phone = seller_phone;
        this.seller_address = seller_address;
    }
}
