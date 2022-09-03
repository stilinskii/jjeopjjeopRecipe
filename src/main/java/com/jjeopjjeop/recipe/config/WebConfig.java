package com.jjeopjjeop.recipe.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HandlerInterceptor authInterceptor;
    public WebConfig(HandlerInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/error/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectPath = "\\var\\lib\\tomcat9\\webapps\\jjeopjjeopRecipe\\WEB-INF\\classes\\static\\media\\";
        registry
                .addResourceHandler("/media/**")
                .addResourceLocations(projectPath);

    }


}
