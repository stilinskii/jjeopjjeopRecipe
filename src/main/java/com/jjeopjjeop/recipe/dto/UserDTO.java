package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.sql.Date;

@Data
@Component
public class UserDTO {
   private String user_id;
   private String password;
   private int usertype;
   @NotBlank(message = "필수 입력 항목입니다.")
   private String username;
   @NotBlank(message = "필수 입력 항목입니다.")
   private String nickname;
   @Email
   private String email;
   @NotBlank(message = "필수 입력 항목입니다.")
   private String phone;
   @Past(message = "미래 날짜로 입력할 수 없습니다.")
   private Date birthday;
   private int gender;
   private int postno;
   private String address;

   public UserDTO(){

   };
}
