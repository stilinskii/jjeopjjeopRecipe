package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.config.MyProperties;
import com.jjeopjjeop.recipe.dto.UserDTO;
import com.jjeopjjeop.recipe.service.UserServiceImp;
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
   public String register(UserDTO userDTO){
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
         model.addAttribute("userDTO", userDTO);
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
   public ModelAndView login(@Validated @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,
                             RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception{
      ModelAndView mav = new ModelAndView();
      userDTO = userServiceImp.login(userDTO);

      //회원이면
      if(userDTO != null){
         HttpSession session = request.getSession();
         session.setAttribute("user", userDTO);
         session.setAttribute("isLogOn", true);
         //login 페이지의 action 가져오기
         String action = (String) session.getAttribute("action");
         session.removeAttribute("action");
         //로그인 실패하면 현재 페이지(로그인)
         if(action != null){
            session.setAttribute("errorMessage", "LoginFail");
            logger.info("Login Fail: ID or Password Doest Not Match");
            mav.setViewName("redirect:/login");
         //로그인 성공 ==> 마이페이지로 이동(임시)
         }else {
            logger.info("Login Success");
            mav.setViewName("users/mypage");
         }
         //회원이 아니라면
      }else {
         rAttr.addAttribute("result", "loginFailed");
         bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
         logger.info("Login Fail: ID or Password Does Not Exist");
         mav.setViewName("redirect:/login");
      }
      return mav;
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
   public String findId(HttpServletRequest request, HttpServletResponse response){
      return "/users/findid";
   }

   //아이디 찾기
   @PostMapping("/findid")
   public String findId(@ModelAttribute("user") UserDTO userDTO, Model model, RedirectAttributes rAttr) throws Exception{
      logger.info("username: "+userDTO.getUsername());
      logger.info("email: "+userDTO.getEmail());
      logger.info("user_id: "+userDTO.getUser_id());
      model.addAttribute("user", userDTO);
      return "/users/findidComplete";
   }

   @PostMapping("/findid/findidComplete")
   @ResponseBody
   public String findidComplete(@RequestParam("username") String username, @RequestParam("email") String email,
                                Model model, HttpServletRequest request, HttpServletResponse response){
      model.addAttribute("user_id", userServiceImp.findId(username, email));
      logger.info(userServiceImp.findId(username, email));
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
//      logger.info(user.getUser_id());

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
}
