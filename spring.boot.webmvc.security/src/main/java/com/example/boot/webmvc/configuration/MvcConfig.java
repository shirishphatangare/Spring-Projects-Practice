package com.example.boot.webmvc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
/*      View Name defined here should be a template from templates folder. 
        Anything outside templates or in static folder will not be found.
        thymeleaf tags on html file will work in templates folder and not in static folder
*/      //registry.addViewController("/greeting").setViewName("greeting"); // Can be defined separately in Controller
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/manager").setViewName("manager");
        registry.addViewController("/accessDenied").setViewName("accessDenied");
    }
}

// Difference between WebMvcConfigurerAdapter and  WebMvcConfigurer