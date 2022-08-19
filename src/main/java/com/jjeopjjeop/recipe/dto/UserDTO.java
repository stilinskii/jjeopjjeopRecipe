package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

@Data
@Component
public class UserDTO {
   @NotBlank(message = "필수 입력 항목입니다.")
   @Pattern(regexp = "[a-z0-9].{4,10}", message = "아이디는 영어 소문자와 숫자 4~10자리여야 합니다.")
   private String user_id;

   @NotBlank(message = "필수 입력 항목입니다.")
//   @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*\\d)(?=.*[$@$!%*#?&]).{5,8}",
//           message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 1개 이상 포함한 5~8자리여야 합니다.")
   private String password;
   private int usertype;

   @NotBlank(message = "필수 입력 항목입니다.")
   @Pattern(regexp = "[가-힣]{2,5}", message = "이름은 한글 2~5자리여야 합니다.")
   private String username;

   @NotBlank(message = "필수 입력 항목입니다.")
   @Pattern(regexp = "[ㄱ-ㅎ가-힣a-z0-9-_]{3,8}", message = "별명은 특수문자를 제외한 3~8자리여야 합니다.")
   private String nickname;

   @NotBlank(message = "필수 입력 항목입니다.")
   @Email(message = "이메일 형식이 올바르지 않습니다.")
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
   
   //조인한 게시판 객체 가져오기
   private List<CommunityDTO> communityDTOList;
}
