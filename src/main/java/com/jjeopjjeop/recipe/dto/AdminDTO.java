package com.jjeopjjeop.recipe.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class AdminDTO {
    private String user_id;
    private String password;
    private Integer usertype;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Date birthday;
    private Integer gender;
    private Integer postno;
    private String address;
}
