package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.UserDAO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.UserDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class UserServiceImp implements UserService{

   @Autowired
   private UserDAO userDAO;

   public UserServiceImp() {

   };

   @Override
   public List<UserDTO> listUsers() {
      return userDAO.selectAllUserList();
   }

   @Override
   public List<CommunityDTO> listMyCommunity(String user_id, Pagenation pagenation) {
      return userDAO.viewMyCommunity(Map.of("user_id", user_id, "page", pagenation));
   }

   @Override
   public List<CommunityDTO> listMyReview(String user_id, Pagenation pagenation) {
      return userDAO.viewMyReview(Map.of("user_id", user_id, "page", pagenation));
   }

   @Override
   public List<RecipeDTO> listMyRecipe(String user_id, Pagenation pagenation) {
      return userDAO.viewMyRecipe(Map.of("user_id", user_id, "page", pagenation));
   }

   @Override
   public List<RecipeDTO> listMyScrap(String user_id, Pagenation pagenation) {
      return userDAO.viewMyScrap(Map.of("user_id", user_id, "page", pagenation));
   }

   @Override
   public int countMyCommunity(String user_id) {
      return userDAO.countMyCommunity(user_id);
   }

   @Override
   public int countMyReview(String user_id) {
      return userDAO.countMyReview(user_id);
   }

   @Override
   public int countMyRecipe(String user_id) {
      return userDAO.countMyRecipe(user_id);
   }

   @Override
   public int countMyScrap(String user_id) {
      return userDAO.countMyScrap(user_id);
   }

   @Override
   public int addUser(UserDTO userDTO) {
      return userDAO.insertUser(userDTO);
   }

   @Transactional
   @Override
   public int removeUser(String user_id, String password) {
      return userDAO.deleteUser(user_id, password);
   }

   @Override
   public UserDTO login(UserDTO userDTO) {
      return userDAO.loginById(userDTO);
   }

   @Override
   public UserDTO findId(UserDTO userDTO) {
      return userDAO.findId(userDTO);
   }

   @Override
   public UserDTO findPassword(UserDTO userDTO) {
      return userDAO.findPassword(userDTO);
   }

   //임시비밀번호를 한 번만 불러오기 위해 멤버변수로 선언
//   String str = getTempPassword();
//   @Override
//   public int updatePassword(UserDTO userDTO) {
//      //임시비밀번호로 비밀번호 업데이트
//      userDTO.setPassword(str);
//      return userDAO.updatePassword(userDTO);
//   }

   //임시비밀번호 생성
//   @Override
//   public String getTempPassword() {
//      char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B',
//              'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
//              'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
//      String str = "";
//
//      //6개 글자를 랜덤으로 뽑아 문자열을 만든다
//      int idx = 0;
//      for (int i=0; i<6; i++){
//         idx = (int) (charSet.length * Math.random());
//         str += charSet[idx];
//      }
//      return str;
//   }

   //메일 보내기 위한 관련 멤버 변수 생성
//   @Autowired
//   private JavaMailSender mailSender;
//   private static final String FROM_ADDRESS="mjkimthebest@gmail.com";

//   @Override
//   public void sendMail(String email) {
//      SimpleMailMessage message = new SimpleMailMessage();
//
//      //메일 수신 테스트를 위해 보내는 이와 받는 이 주소를 동일하게 설정함
//      message.setTo("mjkimthebest@gmail.com");
//      message.setFrom(FROM_ADDRESS);
//      message.setSubject("쩝쩝박사 비밀번호 찾기 안내 메일입니다.");
//      message.setText("비밀번호는 "+ str +"입니다.");
//      mailSender.send(message);
//   }

   @Override
   public UserDTO readMypage(String user_id) {
      return userDAO.readMypage(user_id);
   }

   @Transactional
   @Override
   public void updateMypage(UserDTO userDTO) {
      userDAO.updateMypage(userDTO);
   }

   @Override
   public int checkId(String user_id) {
      return userDAO.checkId(user_id);
   }



   @Override
   public UserDTO findUserById(String user_id) {
      return userDAO.findUserById(user_id);
   }

}
