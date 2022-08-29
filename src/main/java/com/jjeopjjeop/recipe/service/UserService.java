package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.UserDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
   public List<UserDTO> listUsers();
   public List<CommunityDTO> listMyCommunity(String user_id, Pagenation pagenation);
   public List<CommunityDTO> listMyReview(String user_id, Pagenation pagenation);
   public List<RecipeDTO> listMyRecipe(String user_id, Pagenation pagenation);
   public int countMyCommunity(String user_id);
   public int countMyReview(String user_id);
   public int countMyRecipe(String user_id);
   public int addUser(UserDTO userDTO);
   public int removeUser(String user_id, String password);
   public UserDTO login(UserDTO userDTO);
   public UserDTO findId(UserDTO userDTO);
   public UserDTO findPassword(UserDTO userDTO);
   public int updatePassword(UserDTO userDTO);

   public String getTempPassword();
   public void sendMail(String email);

   public UserDTO readMypage(String user_id);

   public void updateMypage(UserDTO userDTO);

   public int checkId(String user_id);

   UserDTO findUserById(String user_id);
}
