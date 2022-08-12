
package com.jjeopjjeop.recipe.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //이미지등록시 서버재가동 없이 바로 볼 수 있게 설정.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectPath = "file:\\" + System.getProperty("user.dir") +"\\src\\main\\resources\\static\\media\\";
        registry
                .addResourceHandler("/media/**")
                .addResourceLocations(projectPath);

    }


}
