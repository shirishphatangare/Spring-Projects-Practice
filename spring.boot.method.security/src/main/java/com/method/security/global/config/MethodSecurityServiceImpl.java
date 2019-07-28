package com.method.security.global.config;

public class MethodSecurityServiceImpl implements 
MethodSecurityService {

public String requiresUserRole() {
  return "You have ROLE_USER";
}
}