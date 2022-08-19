package com.jjeopjjeop.recipe.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

   @Override
   public void customize(ConfigurableWebServerFactory factory) {
      ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
      ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
      ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error-page/400");
      ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

      //에러 페이지 등록
      factory.addErrorPages(errorPage400, errorPage500, errorPage404, errorPageEx);
   }
}
