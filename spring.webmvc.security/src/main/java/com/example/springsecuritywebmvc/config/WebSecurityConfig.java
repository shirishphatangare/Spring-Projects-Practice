package com.example.springsecuritywebmvc.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration // @Configuration - Indicates that a class declares one or more @Bean methods and may be processed by the Spring container

/*@EnableWebSecurity - Add this annotation to an @Configuration class to have the Spring Security configuration defined in any 
WebSecurityConfigurer or more likely by extending the WebSecurityConfigurerAdapter base class and overriding individual methods.*/

@EnableWebSecurity
@ComponentScan(basePackages= "com.example.springsecuritywebmvc")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

/*	@Autowired - Marks a constructor, field, setter method or config method as to be autowired by Spring's dependency injection facilities. 
	This is an alternative to the JSR-330 Inject annotation, adding required-vs-optional semantics.*/
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Autowired 
	Properties userProperties;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
	.antMatchers("/CTC").hasRole("USER")
	.antMatchers("/IST").hasRole("ADMIN")
	.anyRequest().authenticated()
     .and()
     .formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll();
	 //.and()
	 //.logout().permitAll(); // Default logout functionality should be commented in order for custom logout filter 
	                        // added in custom filter chain to take effect
	
	http.exceptionHandling().accessDeniedPage("/accessDeniedPage");
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
            .inMemoryAuthentication().withUser(userProperties.getProperty("user")).password(userProperties.getProperty("userpassword")).roles("USER")
                            .and().withUser(userProperties.getProperty("admin")).password(userProperties.getProperty("adminpassword")).roles("USER", "ADMIN");
            
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

}


