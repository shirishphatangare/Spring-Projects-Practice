package com.example.boot.webmvc.configuration;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionTranslationFilter extends ExceptionTranslationFilter {
	
	public CustomExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
	       super(authenticationEntryPoint);
	}
 }