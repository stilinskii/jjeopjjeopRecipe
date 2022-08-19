package com.jjeopjjeop.recipe.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MySecured {
   //사용할 ROLE 정의
   public enum Role {
      ADMIN, USER;
   }
   //@MySecured(role=Role.ADMIN)과 같이 정의 가능
   Role role() default Role.USER;
}
