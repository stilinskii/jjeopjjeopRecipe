package com.jjeopjjeop.recipe.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\static\\media";
        registry
                .addResourceHandler("/media/**")
                .addResourceLocations(projectPath);
    }
}
