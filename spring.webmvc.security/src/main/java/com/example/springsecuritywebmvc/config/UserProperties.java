package com.example.springsecuritywebmvc.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class UserProperties {
	
   @Autowired
   private Environment env;
	
   @Bean
   @Primary // Using due to bean conflict with systemProperties
   public Properties getUserProperties() {
	   Properties users = new Properties();
	   users.setProperty("user",env.getProperty("credentials.user"));
	   users.setProperty("userpassword",env.getProperty("credentials.userpassword"));
	   users.setProperty("admin",env.getProperty("credentials.admin"));
	   users.setProperty("adminpassword",env.getProperty("credentials.adminpassword"));
	   return users;
   }
	
}
