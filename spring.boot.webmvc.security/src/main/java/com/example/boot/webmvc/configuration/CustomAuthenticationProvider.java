package com.example.boot.webmvc.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {
 
	@Autowired 
	UserDetailsService userDetailsService;
	
	 @Autowired
	 PasswordEncoder passwordEncoder;
	 
	 ProviderManager dfdf;
	
/*	public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("password").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
        //return new InMemoryUserDetailsManager(userDetailsProperties.getUsers());
    }
*/	
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String name = authentication.getName();
        String password = passwordEncoder.encode(authentication.getCredentials().toString());
        
        UserDetails user = userDetailsService.loadUserByUsername(name);
        if(user != null){
            if(user.getUsername().equals("admin") && user.getPassword().equals(password)) {
			return new UsernamePasswordAuthenticationToken(name, password, 
					new ArrayList<GrantedAuthority>(){
						{
							add(new SimpleGrantedAuthority("ROLE_USER"));
							add(new SimpleGrantedAuthority("ROLE_ADMIN"));
						}
						});
			}
			else if(user.getUsername().equals("user") && user.getPassword().equals(password)){
				return new UsernamePasswordAuthenticationToken(name, password, 
						new ArrayList<GrantedAuthority>(){
						{
							add(new SimpleGrantedAuthority("ROLE_USER"));
						}
						});
			}
        }
        throw new BadCredentialsException("Bad Credentials");
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
