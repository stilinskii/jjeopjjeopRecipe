package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.config.MyProperties;
import com.jjeopjjeop.recipe.dto.UserDTO;
import com.jjeopjjeop.recipe.service.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@Controller
public class UserController {

   private static final Logger logger = LoggerFactory.getLogger(UserController.class);

   @Autowired
   private MyProperties prop;
   @Autowired
   private JavaMailSender javaMailSender;
   @Autowired
   private UserDTO userDTO;
   @Autowired
   private UserServiceImp userServiceImp;
   
   //회원가입 페이지
   @GetMapping("/signup")
   public String register(@ModelAttribute("user") UserDTO userDTO){
      //view로 뿌려주려면 @ModelAttribute 설정 필수!
      logger.info("get register");
      return "users/signup";
   }

   //계정 추가(회원가입)
   @PostMapping("/signup")
   public String add(@Validated @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,
                           Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
      //회원가입 검증
      if (bindingResult.hasErrors()){
         //회원가입 실패 시 기존 데이터 유지
         //model.addAttribute("userDTO", userDTO);
         logger.info("errors={}", bindingResult);
         return "users/signup";
      }else {
         int result = 0;
         result = userServiceImp.addUser(userDTO);
         logger.info("post register");
         return "redirect:/login";
      }
   }

   //로그인 페이지
   @GetMapping("/login")
   public String login(UserDTO userDTO){
      logger.info("get login");
      return "users/login";
   }

   //로그인
   @PostMapping("/login")
   public String login(@Validated @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,
                             RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception{
      ModelAndView mav = new ModelAndView();
      userDTO = userServiceImp.login(userDTO);

      //회원이면
      if(userDTO != null){
         HttpSession session = request.getSession();
         session.setAttribute("user", userDTO);
         session.setAttribute("user_id", userDTO.getUser_id());
         session.setAttribute("isLogOn", true);
         //login 페이지의 action 가져오기
         String action = (String) session.getAttribute("action");
         session.removeAttribute("action");
         //로그인 실패하면 현재 페이지(로그인)
         if(action != null){
            session.setAttribute("errorMessage", "LoginFail");
            logger.info("Login Fail: ID or Password Doest Not Match");
            return "redirect:/login";
         //로그인 성공 ==> 마이페이지로 이동(임시)
         }else {
            logger.info("Login Success");
            return "redirect:/mypage";
         }
         //회원이 아니라면
      }else {
         rAttr.addAttribute("result", "loginFailed");
         bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
         logger.info("Login Fail: ID or Password Does Not Exist");
         return "redirect:/login";
      }

   }


   //로그아웃 페이지
   @GetMapping("/logout")
   public ModelAndView logout(HttpServletRequest request){
      ModelAndView mav = new ModelAndView("redirect:/login");
      HttpSession session = request.getSession();
      //로그인 중인 세션을 모두 사라지게 만들기==>로그아웃
      session.invalidate();
      logger.info("Logout Success");
      return mav;
   }

   //아이디 찾기 페이지
   @GetMapping("/findid")
   public String findId(@ModelAttribute UserDTO userDTO, Model model, HttpServletRequest request, HttpServletResponse response){
      model.addAttribute("user", userDTO);
      return "/users/findid";
   }

   //아이디 찾기
   @PostMapping("/findid")
   public String findId(@ModelAttribute("userDTO") UserDTO userDTO, Model model, RedirectAttributes rAttr) throws Exception{
//      logger.info("username: "+userDTO.getUsername());
//      logger.info("email: "+userDTO.getEmail());
//      logger.info("user_id: "+userDTO.getUser_id());
//      logger.info(userServiceImp.findId(userDTO).getUsername());
//      logger.info(userServiceImp.findId(userDTO).getUser_id());
      userDTO = userServiceImp.findId(userDTO);
//      logger.info("user_id: "+userDTO.getUser_id());
      model.addAttribute("user", userServiceImp.findId(userDTO));
      return "/users/findidComplete";
   }

   //비밀번호 찾기 페이지
   @GetMapping("/findpw")
   public String findpw(String username, String email){
      return "/users/findpw";
   }

   //인증번호 발송
   @PostMapping("/findpw")
   public String sendEmail(UserDTO userDTO, Model model){
      UserDTO user = userServiceImp.findPassword(userDTO);
      logger.info(userDTO.getUser_id());

      if(user == null){
         model.addAttribute("message", "아이디나 이메일이 일치하는 사용자가 없습니다");
         return "users/findpw";
      }else {

         String email = user.getEmail();

         userServiceImp.updatePassword(userDTO);
         userServiceImp.sendMail(email);

         logger.info(email);
         return "users/findpwComplete";
      }
   }

   //마이페이지
   @GetMapping("/mypage")
   public String viewMypage(String user_id, HttpSession session, Model model){
      user_id = (String) session.getAttribute("user_id");
      UserDTO userDTO = userServiceImp.readMypage(user_id);
//      UserDTO userDTO = (UserDTO) session.getAttribute("user");
      model.addAttribute("user", userDTO);
//      System.out.println(userDTO.getPassword());

      return "users/mypage";
   }

   //마이페이지 수정
   @GetMapping("/mypage/edit")
   public String editMypageView(String user_id, HttpSession session, Model model){
      logger.info("update Mypage");
      user_id = (String) session.getAttribute("user_id");
      UserDTO userDTO = userServiceImp.readMypage(user_id);
//      System.out.println(userDTO.getPassword());
      model.addAttribute("user", userDTO);
      return "users/mypageEdit";
   }

   @PostMapping("/mypage/edit")
   public String updateMypage(@ModelAttribute("user") UserDTO userDTO, Model model){
      userServiceImp.updateMypage(userDTO);
      model.addAttribute("user", userDTO);
      return "redirect:/mypage";
   }


   //회원탈퇴
   @GetMapping("/mypage/withdraw")
   public String deleteAccount() {
      logger.info("get withdraw");
      return "users/withdraw";
   }

   @PostMapping("/mypage/withdraw")
   public String deleteAccount(String user_id, String password, @ModelAttribute UserDTO userDTO, HttpSession session,
                               RedirectAttributes rAttr, Model model) throws Exception{
      UserDTO user = (UserDTO) session.getAttribute("user");
      String sessionPassword = user.getPassword();
      String userPassword = userDTO.getPassword();
      System.out.println("session: "+sessionPassword);
      System.out.println("DTO: "+userPassword);

      if(!(sessionPassword.equals(userPassword))){
         rAttr.addFlashAttribute("message", false);
         return "redirect:/mypage/withdraw";
      }else {
         logger.info("post withdraw");
         userServiceImp.removeUser(userDTO);
         System.out.println(userServiceImp.removeUser(userDTO));
         session.invalidate();
         return "users/withdrawComplete";
      }
//      boolean result = userServiceImp.checkPassword(user_id, password);
//      System.out.println(result);
//      if(result){
//         userServiceImp.removeUser(userDTO);
//         return "users/withdrawComplete";
//      }else {
//         model.addAttribute("message", "비밀번호 불일치");
//         return "redirect:/mypage/withdraw";
//      }
   }

   //내 게시글 보기
   @GetMapping("/mypage/board")
   public String showMyCommunity(String user_id, HttpSession session){
      logger.info("Get myboard");

      return "users/myCommunity";
   }

   @PostMapping("/mypage/board")
   public String postMyCommunity(String user_id, HttpSession session, Model model){
      logger.info("Post myboard");
      user_id = (String) session.getAttribute("user_id");
      System.out.println("user_id: " + user_id);
      List<UserDTO> myCommunityList = userServiceImp.listMyCommunity(user_id);
      System.out.println(myCommunityList);
      model.addAttribute("myCommunityList", myCommunityList);
      return "users/myCommunity";
   }
}
