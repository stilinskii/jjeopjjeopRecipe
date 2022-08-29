package com.jjeopjjeop.recipe.config;

import com.jjeopjjeop.recipe.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      // handler 종류 확인 => HandlerMethod 타입인지 체크
      // HandlerMethod가 아니면 그대로 진행
      if (!(handler instanceof HandlerMethod)) {
         return true;
      }

      // 형 변환 하기
      HandlerMethod handlerMethod = (HandlerMethod) handler;

      // @MySecured 받아오기
      MySecured mySecured = handlerMethod.getMethodAnnotation(MySecured.class);

      // method에 @MySecured가 없는 경우 ==> 인증이 필요 없는 요청
      if (mySecured == null) {
//         log.info(String.valueOf(mySecured));
         return true;
      }

      // @MySecured가 있는 경우 ==> 세션이 있는지 체크
      HttpSession session = request.getSession();
      if (session == null) {
//         log.info(String.valueOf(session));
         response.sendRedirect("/login");
         return false;
      }

      // 세션이 존재하면 유효한 유저인지 확인
      UserDTO user = (UserDTO) session.getAttribute("user");
      if (user == null) {
//         log.info(String.valueOf(user));
         response.sendRedirect("/login");
         return false;
      }

      // admin일 경우
      String role = mySecured.role().toString();
      if(role != null) {
         if ("ADMIN".equals(role)) {
            if( "admin".equals(user.getUser_id()) == false ) {
               response.sendRedirect("/");
               return false;
            }
         }
      }

      // 접근허가
      return true;
   }


   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

   }

   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

   }
}
