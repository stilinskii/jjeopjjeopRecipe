package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.UserDTO;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface UserService {
   public List<UserDTO> listUsers();
   public int addUser(UserDTO userDTO);
   public int removeUser(String user_id);
   public UserDTO login(UserDTO userDTO);
   public String findId(String username, String email);
   public UserDTO findPassword(UserDTO userDTO);
   public int updatePassword(UserDTO userDTO);

   public String getTempPassword();
   public void sendMail(String email);

}
