package com.jjeopjjeop.recipe.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@Component
@NoArgsConstructor
public class SellerDTO {
    private String user_id;
    @NotBlank(message = "상호명을 입력해주세요.")
    private String business_name;
    @NotNull(message = "사업자등록번호를 입력해주세요.")
    private String registration_number;
    @NotBlank(message = "사업장 전화번호를 입력해주세요.")
    private String seller_phone;
    @NotBlank(message = "사업장 주소를 입력해주세요.")
    private String seller_address;
    private Integer approval;

    public SellerDTO(String user_id, String business_name, String registration_number, String seller_phone, String seller_address) {
        this.user_id = user_id;
        this.business_name = business_name;
        this.registration_number = registration_number;
        this.seller_phone = seller_phone;
        this.seller_address = seller_address;
    }
}
