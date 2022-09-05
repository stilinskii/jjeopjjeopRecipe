package com.jjeopjjeop.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//에러 페이지 컨트롤러: 에러코드에 따라 에러 페이지를 불러옴
@Slf4j
@Controller
public class ErrorPageController {

   @GetMapping("/error-page/400")
   public String errorPage400(HttpServletRequest request, HttpServletResponse response){
//      log.info("errorPage400");
      return "error/400";
   }

   @GetMapping("/error-page/500")
   public String errorPage500(HttpServletRequest request, HttpServletResponse response){
//      log.info("errorPage500");
      return "error/500";
   }

   @GetMapping("/error-page/404")
   public String errorPage404(HttpServletRequest request, HttpServletResponse response){
//      log.info("errorPage404");
      return "error/404";
   }

   public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
   public static final String ERROR_MESSAGE = "javax.servlet.error.message";

   public void printInfo(HttpServletRequest request){
      log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
      log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
   }
}
