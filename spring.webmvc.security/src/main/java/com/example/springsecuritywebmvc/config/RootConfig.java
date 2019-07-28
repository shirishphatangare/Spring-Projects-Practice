package com.example.springsecuritywebmvc.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackages = "com.example.springsecuritywebmvc")
public class RootConfig {
	
	@Bean // Bean annotation indicates that a method produces a bean to be managed by the Spring container.
	public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
	{
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("application.properties"));
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
}