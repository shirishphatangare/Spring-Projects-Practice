package com.method.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // @Configuration classes are bootstrapped using component scanning
@EnableWebSecurity // Add this annotation to an @Configuration class to have the Spring Security configuration defined in  
				   // any WebSecurityConfigurer or more likely by extending the WebSecurityConfigurerAdapter base class
//@EnableGlobalMethodSecurity(prePostEnabled = true) // enables Spring Security pre/post annotations
//@EnableGlobalMethodSecurity(securedEnabled = true) // determines if the @Secured annotation should be enabled
//@EnableGlobalMethodSecurity(jsr250Enabled = true) // allows us to use the @RolesAllowed annotation
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Autowired
	CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
    @Override
    public void configure(WebSecurity web) throws Exception {
            web.ignoring()
            .antMatchers("/templates/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	.authorizeRequests() 
    	.antMatchers("/favicon.ico").permitAll() // /favicon.ico prevents unnecessary icon display
    	//.antMatchers("/greeting").hasRole("USER")
    	//.antMatchers("/manager").hasRole("ADMIN")
    	.anyRequest().authenticated() 
    	.and()   
    	.formLogin() 
        .loginPage("/login").defaultSuccessUrl("/list")
        .permitAll()
    	.and()  
    	.logout() 
    	.permitAll(); 
    	
    	http.exceptionHandling().accessDeniedPage("/authorizationDenied");
    	
    	// AuthenticationEntryPoint should be customized for REST endpoints and not for GUI based MVC web applications
    	// By default AuthenticationEntryPoint is /login page
    	//http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
    	
    	
    	// Http Response headers management - Mainly for security concerns
		//http.headers().disable();
		//http.header.defaultsDisabled();
		
/*		http.headers()
			.cacheControl()
			.and().contentTypeOptions()
			.and().httpStrictTransportSecurity()
					.includeSubDomains(true)
					.maxAgeInSeconds(31536000)
			.and().httpPublicKeyPinning()
					.includeSubDomains(true)
					.reportUri("https://report-uri.io/")  //<Reporting URL - https://report-uri.io/>
					.addSha256Pins("U3ByaW5nU2VjdXJpdHlJbkVhc3lTdGVwcw==") //< Base64 encoded Subject Public Key Information (SPKI) fingerprint >
			.and().xssProtection().block(false)
			.and().addHeaderWriter(new StaticHeadersWriter("Custom-Security-Header","value"));
*/    
    	
    
    	//Http Session management
/*		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.maximumSessions(1).expiredUrl("/login")
		.and().invalidSessionUrl("/login") // If specified, logout().delete-cookies("JSESSIONID") should be specified as below
		.sessionFixation().changeSessionId();
*/	
		//http.logout().deleteCookies("JSESSIONID").invalidateHttpSession(true);

    	
    }

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authProvider); // Provide custom authenticationProvider 
    }
    
    
    // NoOpPasswordEncoder is deprecated - Do not use on production - For testing only!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    
	 
}

