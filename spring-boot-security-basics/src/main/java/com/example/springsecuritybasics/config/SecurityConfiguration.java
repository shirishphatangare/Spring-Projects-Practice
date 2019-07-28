package com.example.springsecuritybasics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // @Configuration classes are bootstrapped using component scanning
@EnableWebSecurity // Add this annotation to an @Configuration class to have the Spring Security configuration defined in  
				   // any WebSecurityConfigurer or more likely by extending the WebSecurityConfigurerAdapter base class
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enables Spring Security global method security 
// Parameter prePostEnabled - Determines if Spring Security's pre post annotations (like @PreAuthorize and @PostAuthorize Annotations) 
// should be enabled. Default is false.
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Autowired
	CustomFilter customFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
            web.ignoring()
            // Spring Security should completely ignore URLs starting with /resources/
            // Ignore web security for static resources
                            .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests() 
    	.antMatchers("/home").permitAll() // Specify that "/home" URL are allowed by all - No Authentication or Authorization is required
    	.antMatchers("/publicTime").hasRole("USER")  // Specify that for "/publicTime" URL - Authentication is required and For authorization role must be "USER"
    	.antMatchers("/secretTime").hasRole("ADMIN") // Specify that for "/secretTime" URL - Authentication is required and For authorization role must be "ADMIN"
    	//.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") // Any URL that starts with "/db/" requires the user to have both "ROLE_ADMIN" and "ROLE_DBA". 
    													 // You will notice that since we are using the hasRole expression we do not need to specify the "ROLE_" prefix.
    	.anyRequest().authenticated() // Apart from "/publicTime" and "/secretTime" URLs - Just need authentication and No Authorization
    	.and()   //  more configuration ...
    	.formLogin()  // enable form based log in 
        //.loginPage("/login") // Explicitly state the login page for form based login
        .permitAll() // No Authentication or Authorization is required for "/login" and "/login?error" - Allow access to any URL that formLogin() uses.
    	.and()
    	.httpBasic(); // Authenticate using Form based authentication OR Http basic authentication
    	//.and()
    	//.requiresChannel().anyRequest().requiresSecure(); // Configures Channel security. Require HTTPs for every request
    	//.and()    //  more configuration ...
    	//.logout() // Provides logout support. This is automatically applied when using WebSecurityConfigurerAdapter. 
    	          // The default is that accessing the URL"/logout" will log the user out by invalidating the HTTP Session, 
    	          // cleaning up any rememberMe() 
    	          // authentication that was configured, clearing the SecurityContextHolder, and then redirect to "/login?success". 
    	//.permitAll(); // Grants access to the logoutSuccessUrl(String) and the logoutUrl(String) for every user.
    	
    	/*sample logout customization
			.logout().deleteCookies("remove").invalidateHttpSession(false)
			.logoutUrl("/custom-logout").logoutSuccessUrl("/logout-success");*/
    	
    	//http.addFilter(filter) // Adds a Filter that must be an instance of or extend one of the Filters provided within the Security framework. 
    						     // The method ensures that the ordering of the Filters is automatically taken care of (default ordering).
    	//http.addFilterAt(customFilter, UsernamePasswordAuthenticationFilter.class); // Adds the Filter at the default location of UsernamePasswordAuthenticationFilter
    																				  // That mean ordering of customFilter and UsernamePasswordAuthenticationFilter is not guaranteed
    	//http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    	http.addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class); 
    	
    	//http.exceptionHandling().accessDeniedPage("/accessDeniedPage"); // the endpoint to the access denied page (i.e. /errors/401)
    }

    
    /* For below Configure method to work for Authentication, we have to provide custom authenticationProvider and 
     * userDetailsService. Here inMemoryAuthentication ensures that a UserDetailsService is available
     */
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* inMemoryAuthentication and jdbcAuthentication methods ensure that a UserDetailsService is available for the getDefaultUserDetailsService() method. 
         * Note that additional UserDetailsService may override this UserDetailsService as the default
        */ 
    	
    	auth
            // enable in memory based authentication with a user named "user" and "admin"
            .inMemoryAuthentication().withUser("user").password("password").roles("USER")
                            .and().withUser("admin").password("password").roles("USER", "ADMIN");
            
            //auth.jdbcAuthentication() // Add JDBC authentication to the AuthenticationManagerBuilder and return a JdbcUserDetailsManagerConfigurer to allow customization of the JDBCauthentication. 
            //auth.ldapAuthentication() // Add LDAP authentication to the AuthenticationManagerBuilder and return a LdapAuthenticationProviderConfigurer 
                                        // to allow customization of the LDAPauthentication. 
            						    // This method does NOT ensure that a UserDetailsService is available for the 
                                        // getDefaultUserDetailsService() method.	

    	// Either provide userDetailsService() in CustomAuthenticationProvider itself or use it 
    	// as a bean exposed from this class. Both ways work
    	auth.authenticationProvider(authProvider); // Provide custom authenticationProvider 
    }
    
    // Expose the UserDetailsService as a Bean
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
           return super.userDetailsServiceBean();
    }
    
    
    /* When below method userDetailsService is added, both passwords (from configure() method above and userDetailsService method below) are working */
    
    /*@Bean
    @Override
    public UserDetailsService userDetailsService() {
      UserDetails user =
          User.withDefaultPasswordEncoder() // This method is considered unsafe for production and is only intended for sample applications. 
          									// External password encoding is always safe					
              .username("user1")
              .password("password1")
              .roles("USER")
              .build();

      return new InMemoryUserDetailsManager(user); // UserDetailsManager extends UserDetailsService
      											   // With constructor InMemoryUserDetailsManager(Properties users) we can pass users as properties read from yml file								
    }*/

}
