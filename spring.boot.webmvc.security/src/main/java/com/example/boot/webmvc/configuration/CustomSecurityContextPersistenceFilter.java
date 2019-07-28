package com.example.boot.webmvc.configuration;

import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomSecurityContextPersistenceFilter extends SecurityContextPersistenceFilter{

	public CustomSecurityContextPersistenceFilter(SecurityContextRepository repo) {
		super(repo);
	}
}

