package com.example.springsecuritywebmvc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class CustomSpringSecurityFilterChain extends FilterChainProxy{

	public CustomSpringSecurityFilterChain() {
		super(filterChains());
	}

	private static List<SecurityFilterChain> filterChains(){
		List<SecurityFilterChain> filterChain = new ArrayList<SecurityFilterChain>();
		LogoutFilter customLogoutFilter = new LogoutFilter(new CustomLogoutSuccessHandler(), new SecurityContextLogoutHandler());
		customLogoutFilter.setFilterProcessesUrl("/customlogout");
		filterChain.add(new DefaultSecurityFilterChain(
				new AntPathRequestMatcher("/customlogout**"), customLogoutFilter));
		return filterChain;	
	}

}
