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
   @Pattern(regexp = "^[a-z]{1}([a-z0-9]){2,9}$", message = "아이디는 영어 소문자로 시작하고 영어 소문자 또는 숫자를 포함하여 총 3~10자리어야 합니다.")
   private String user_id;

   @NotBlank(message = "필수 입력 항목입니다.")
//   @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*\\d)(?=.*[$@$!%*#?&]).{5,8}",
   @Pattern(regexp = "(?=.*[A-Za-z])([0-9A-Za-z$@$!%*#?&]){3,8}",
           message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 1개 이상 포함한 3~8자리여야 합니다.")
   private String password;
   private int usertype;

   @NotBlank(message = "필수 입력 항목입니다.")
   @Pattern(regexp = "[가-힣]{2,5}", message = "이름은 한글 2~5자리여야 합니다.")
   private String username;

   @NotBlank(message = "필수 입력 항목입니다.")
   @Pattern(regexp = "[ㄱ-ㅎ가-힣a-z0-9]{2,5}", message = "별명은 특수문자를 제외한 2~5자리여야 합니다.")
   private String nickname;

   @NotBlank(message = "필수 입력 항목입니다.")
   @Email(message = "이메일 형식이 올바르지 않습니다.")
   private String email;

   @NotBlank(message = "필수 입력 항목입니다.")
   @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
   private String phone;

//   @Past(message = "미래 날짜로 입력할 수 없습니다.") //자바스크립트로 대체
   private Date birthday;

   private int gender;
   private int postno;
   private String address;

   public UserDTO(){

   };
   
   //조인한 게시판 객체 가져오기
//   private List<CommunityDTO> communityDTOList;
}
