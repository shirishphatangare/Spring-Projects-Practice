package com.method.security.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.method.security.dao.EmployeeRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class CustomPermissionEvaluatorWebSecurityConfig extends GlobalMethodSecurityConfiguration {

	@Autowired
	EmployeeRepository employeeRepository;
	
 /* @Bean
  public MethodSecurityService methodSecurityService() {
    return new MethodSecurityServiceImpl();
  }
*/
  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator(employeeRepository));
    return expressionHandler;
  }
}
  
