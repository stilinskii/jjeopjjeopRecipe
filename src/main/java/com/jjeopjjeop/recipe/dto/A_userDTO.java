package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@Component
public class A_userDTO {
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
