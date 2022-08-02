//package com.jjeopjjeop.recipe.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.CacheControl;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.concurrent.TimeUnit;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    // 캐시 정책 적용
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        CacheControl cacheControl = CacheControl
////                .noCache();
//                .maxAge(5, TimeUnit.SECONDS);
//
//        registry.addResourceHandler("**")
//                .addResourceLocations("resources/static/")
//                .setCacheControl(cacheControl);
//    }
//}