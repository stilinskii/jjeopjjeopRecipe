package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.UserDTO;

import java.util.List;

public interface UserService {
   public List<UserDTO> listUsers();
   public List<UserDTO> listMyCommunity(String user_id);
   public int addUser(UserDTO userDTO);
   public int removeUser(UserDTO userDTO);
   public UserDTO login(UserDTO userDTO);
   public UserDTO findId(UserDTO userDTO);
   public UserDTO findPassword(UserDTO userDTO);
   public int updatePassword(UserDTO userDTO);

   public String getTempPassword();
   public void sendMail(String email);

   public UserDTO readMypage(String user_id);

   public void updateMypage(UserDTO userDTO);

   public boolean checkPassword(String user_id, String password);
}
