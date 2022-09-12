package com.jjeopjjeop.recipe.controller;


import com.jjeopjjeop.recipe.config.MySecured;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.dto.UserDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;


@Slf4j
@Controller
public class UserController {

   private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//   @Autowired
//   private MyProperties prop;
//   @Autowired
//   private JavaMailSender javaMailSender;
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
   public String add(@Validated @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, RedirectAttributes rAttr,
                           Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
      //회원가입 검증
      if (bindingResult.hasErrors()){
         //회원가입 실패 시 기존 데이터 유지
         //model.addAttribute("userDTO", userDTO);
//         logger.info("errors={}", bindingResult);
//         model.addAttribute("message", "형식에 맞지 않는 값이 입력되었습니다.");
         return "users/signup";
      }else {
         int result = 0;
         result = userServiceImp.addUser(userDTO);
         logger.info("post register");
         rAttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
         return "redirect:/login";
      }
   }

   //아이디 중복 확인
   @ResponseBody
   @GetMapping("/signup/idCheck")
   public int userIdExist(String user_id, Model model){
      int result = userServiceImp.checkId(user_id);
//      log.info("idCheck result: " + user_id);
//      log.info("checkId result: " + result);
      model.addAttribute("user_id", user_id);
      return result;
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
            session.setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
            logger.info("Login Fail: ID or Password Doest Not Match");
            return "redirect:/login";
         //로그인 성공 ==> 마이페이지로 이동(임시)
         }else {
            rAttr.addFlashAttribute("message", "로그인 되었습니다.");
            logger.info("Login Success");
            return "redirect:/";
         }
         //회원이 아니라면
      }else {
         rAttr.addFlashAttribute("message", "등록된 아이디가 없습니다.");
         bindingResult.reject("loginFail", "로그인 실패");
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
   public String findId(@ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,
                        Model model, RedirectAttributes rAttr) throws Exception{
//      logger.info("username: "+userDTO.getUsername());
//      logger.info("email: "+userDTO.getEmail());
//      logger.info("user_id: "+userDTO.getUser_id());
//      logger.info(userServiceImp.findId(userDTO).getUsername());
//      logger.info(userServiceImp.findId(userDTO).getUser_id());
      userDTO = userServiceImp.findId(userDTO);
//      logger.info("user_id: "+userDTO.getUser_id());
      if(bindingResult.hasErrors()){
         model.addAttribute("message", "이름이 누락되었습니다.");
         return "/users/findid";
      } else if(userDTO == null){
         rAttr.addFlashAttribute("message", "이름 또는 이메일이 일치하지 않습니다.");
         return "redirect:/findid";
      } else{
         model.addAttribute("user", userServiceImp.findId(userDTO));
         return "/users/findidComplete";
      }

   }

   //비밀번호 찾기 페이지
   @GetMapping("/findpw")
   public String findpw(String username, String email){
      return "/users/findpw";
   }

   //인증번호 발송 ===> application.properties 노출로 보안 이슈 발생 ==> 기존 아이디 찾기와 동일한 방식으로 변경
   @PostMapping("/findpw")
   public String sendEmail(@ModelAttribute("user") UserDTO userDTO, Model model, RedirectAttributes rAttr) throws Exception{
      userDTO = userServiceImp.findPassword(userDTO);
//      logger.info("userDTO={}", userDTO);

      if(userDTO == null){
         rAttr.addFlashAttribute("message", "아이디와 이메일이 일치하지 않습니다.");
         return "redirect:/findpw";
      }else {

//         String email = userDTO.getEmail();
//
//         userServiceImp.updatePassword(userDTO);
//         userServiceImp.sendMail(email);
//
//         logger.info(email);
           model.addAttribute("user", userDTO);

         return "users/findpwComplete";
      }
   }

   //마이페이지
   @MySecured(role = MySecured.Role.USER)
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
   @MySecured
   @GetMapping("/mypage/edit")
   public String editMypageView(String user_id, HttpSession session, Model model){
      logger.info("update Mypage");
      user_id = (String) session.getAttribute("user_id");
      UserDTO userDTO = userServiceImp.readMypage(user_id);
//      System.out.println(userDTO.getPassword());
//      log.info("userDTO={}", userDTO);
      model.addAttribute("user", userDTO);
      return "users/mypageEdit";
   }

   @PostMapping("/mypage/edit")
   public String updateMypage(@Validated @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,
                              RedirectAttributes rAttr, Model model){
      if(bindingResult.hasErrors()){
//         logger.info("errors={}", bindingResult);
         model.addAttribute("message", "형식에 맞지 않는 값이 입력되었습니다.");
         return "users/mypageEdit";
      }else{
         userServiceImp.updateMypage(userDTO);
         model.addAttribute("user", userDTO);
         rAttr.addFlashAttribute("message", "내 정보가 수정되었습니다.");
         return "redirect:/mypage";
      }
   }


   //회원탈퇴
   @MySecured
   @GetMapping("/mypage/withdraw")
   public String deleteAccount() {
      logger.info("get withdraw");
      return "users/withdraw";
   }

   @PostMapping("/mypage/withdraw")
   public String deleteAccount(String user_id, String password, HttpSession session,
                               RedirectAttributes rAttr, Model model) throws Exception{

      UserDTO user = (UserDTO) session.getAttribute("user");
      String sessionId = user.getUser_id();
      String sessionPassword = user.getPassword();

//      System.out.println("session: "+sessionPassword);
//      System.out.println("Input: "+password);

      if(!(sessionId.equals(user_id))){
         rAttr.addFlashAttribute("message", "아이디가 일치하지 않습니다.");
         return "redirect:/mypage/withdraw";
      } else if (!(sessionPassword.equals(password))) {
         rAttr.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
         return "redirect:/mypage/withdraw";
      } else {
         logger.info("post withdraw");
         userServiceImp.removeUser(user_id, password);
//         System.out.println(userServiceImp.removeUser(user_id, password));
         session.invalidate();
         return "users/withdrawComplete";
      }
   }

   //내 게시글 보기
   @MySecured
   @GetMapping("/mypage/board")
   public String showMyCommunity(@RequestParam(value = "user_id", required = false, defaultValue = "") String user_id,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 HttpSession session, Model model){
      logger.info("Get myBoard");
      user_id = (String) session.getAttribute("user_id");
//      System.out.println("user_id: " + user_id);
      Pagenation pagenation = new Pagenation(page, 5, userServiceImp.countMyCommunity(user_id));
      List<CommunityDTO> communityDTOList = userServiceImp.listMyCommunity(user_id, pagenation);
//      log.info("communityDTOList={}",communityDTOList.get(0).getUser_id());

      model.addAttribute("user_id", user_id);
      model.addAttribute("myCommunityList", communityDTOList);
      model.addAttribute("page", pagenation);
//      System.out.println("page= "+page);
//      System.out.println("startRow: "+pagenation.getStartRow()+" / endRow: "+pagenation.getEndRow());
//      System.out.println("myCommunityList: " + communityDTOList);
//      System.out.println("page: " + pagenation);
      return "users/myCommunity";
   }

   //내 레시피 후기 보기
   @MySecured
   @GetMapping("/mypage/review")
   public String showMyReview(@RequestParam(value = "user_id", required = false, defaultValue = "") String user_id,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 HttpSession session, Model model){
      logger.info("Get myReview");
      user_id = (String) session.getAttribute("user_id");

      Pagenation pagenation = new Pagenation(page, 5, userServiceImp.countMyReview(user_id));
      List<CommunityDTO> communityDTOList = userServiceImp.listMyReview(user_id, pagenation);

      model.addAttribute("user_id", user_id);
      model.addAttribute("myReviewList", communityDTOList);
      model.addAttribute("page", pagenation);

      return "users/myReview";
   }

   //내 레시피 보기
   @MySecured
   @GetMapping("/mypage/recipe")
   public String showMyRecipe(String user_id, RecipePageDTO recipePageDTO, @RequestParam(value="page", required = false, defaultValue = "0") int page,
                              HttpSession session, Model model){
      logger.info("Get myRecipe");

      user_id = (String) session.getAttribute("user_id");
      Pagenation pagenation = new Pagenation(page, 5, userServiceImp.countMyRecipe(user_id));

      List<RecipeDTO> recipeDTOList = userServiceImp.listMyRecipe(user_id, pagenation);

      model.addAttribute("myRecipeList", recipeDTOList);
//      model.addAttribute("recipePageDTO", recipePageDTO);
      model.addAttribute("page", pagenation);
//      System.out.println("page= "+page);
//      System.out.println("startRow: "+pagenation.getStartRow()+" / endRow: "+pagenation.getEndRow());
//      System.out.println("myRecipeList: " + recipeDTOList);
//      System.out.println("page: " + pagenation);

      return "users/myRecipe";
   }

   //내가 스크랩한 레시피 보기
   @MySecured
   @GetMapping("/mypage/scrap")
   public String showMyScrap(String user_id, RecipePageDTO recipePageDTO, @RequestParam(value="page", required = false, defaultValue = "0") int page,
                              HttpSession session, Model model){
      user_id = (String) session.getAttribute("user_id");
      Pagenation pagenation = new Pagenation(page, 5, userServiceImp.countMyScrap(user_id));

      List<RecipeDTO> recipeDTOList = userServiceImp.listMyScrap(user_id, pagenation);

      model.addAttribute("myRecipeList", recipeDTOList);
      model.addAttribute("page", pagenation);

      return "users/myScrap";
   }


}
