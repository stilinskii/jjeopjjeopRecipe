package com.jjeopjjeop.recipe.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class MyProperties {

   //인증메일 변수
   @Value("${spring.mail.host}")
   private String mailHost;
   @Value("${spring.mail.port}")
   private String mailPort;
   @Value("${spring.mail.username}")
   private String mailUsername;
   @Value("${spring.mail.password}")
   private String mailPassword;
   @Value("${spring.mail.properties.mail.smtp.auth}")
   private String mailAuth;
   @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
   private String mailEnable;

}
