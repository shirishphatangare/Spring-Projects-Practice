package com.example.boot.webmvc.configuration;

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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration // @Configuration classes are bootstrapped using component scanning
@EnableWebSecurity // Add this annotation to an @Configuration class to have the Spring Security configuration defined in  
				   // any WebSecurityConfigurer or more likely by extending the WebSecurityConfigurerAdapter base class
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
    @Override
    public void configure(WebSecurity web) throws Exception {
            web.ignoring()
            .antMatchers("/templates/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	.authorizeRequests() 
    	.antMatchers("/","/home","/favicon.ico").permitAll() // /favicon.ico prevents unnecessary icon display
    	.antMatchers("/greeting").hasRole("USER")
    	.antMatchers("/manager").hasRole("ADMIN")
    	.anyRequest().authenticated() 
    	.and()   
    	.formLogin() 
        .loginPage("/login").defaultSuccessUrl("/greeting")
        .permitAll()
    	.and()  
    	.logout() 
    	.permitAll(); 
    	
    	//http.exceptionHandling().accessDeniedPage("/accessDenied");
    	
    	http.addFilterAfter(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);
    	http.addFilterAfter(securityContextPersistenceFilter(), SecurityContextPersistenceFilter.class);
 		http.addFilterAfter(exceptionTranslationFilter(), ExceptionTranslationFilter.class);
		//http.addFilter(filterSecurityInterceptor()); // This ensures filter ordering by default
    }

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
            .inMemoryAuthentication().withUser("user").password("password").roles("USER")
                            .and().withUser("admin").password("password").roles("USER", "ADMIN");
    	auth.authenticationProvider(authProvider); // Provide custom authenticationProvider 
    }
    
    // Expose the UserDetailsService as a Bean
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
           return super.userDetailsServiceBean();
    }
    
    // NoOpPasswordEncoder is deprecated - Do not use on production - For testing only!
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    
/*    Below are core security filters - Filters which are by default executed every time in a Spring security application
    1) FilterSecurityInterceptor - Comes at the end of filter ordering in filter chain of spring security
    2) ExceptionTranslationFilter - Needs AuthenticationEntryPoint, AccessDeniedHandler and RequestCache
	3) SecurityContextPersistenceFilter - Stores security context and requires SecurityContextRepository
    4) UsernamePasswordAuthenticationFilter - For username/password authentication - Always executes!
*/    

   // To implement FilterSecurityInterceptor - Need deeper understanding of Authorization architecture (AccessDecisionManager and AccessDecisionVoter)  
   /*	@Bean
	FilterSecurityInterceptor filterSecurityInterceptor() throws Exception{
		FilterSecurityInterceptor fSecInterceptor=new FilterSecurityInterceptor();
		fSecInterceptor.setAccessDecisionManager(accessDecisionManager());
		fSecInterceptor.setAuthenticationManager(super.authenticationManager());
		fSecInterceptor.setSecurityMetadataSource(
				new DefaultFilterInvocationSecurityMetadataSource(
						(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>) new LinkedHashMap().put(
								new AntPathMatcher("/greeting**"), new ArrayList().add("ROLE_ADMIN"))));
		return fSecInterceptor;
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
	    List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
	    decisionVoters.add(new RoleVoter());
	    decisionVoters.add(new AuthenticatedVoter());
	    return new AffirmativeBased(decisionVoters);
	}*/
    
    /* While creating beans for core filters below, I observed 2 important things - 
     * 1) Always create a Custom bean class (CustomExceptionTranslationFilter) which extends original filter class (ExceptionTranslationFilter)
     * 2) Created CustomExceptionTranslationFilter as a Component and pass required custor argumats to Custom class as Beans (customAuthenticationEntryPoint())
     * 3) Add custom filter classe (CustomExceptionTranslationFilter) after original Filter class (i.e. use addFilterAfter)
     */
	
	@Bean
	public CustomExceptionTranslationFilter exceptionTranslationFilter(){
		AccessDeniedHandlerImpl accessDeniedHandlerImpl=new AccessDeniedHandlerImpl();
		accessDeniedHandlerImpl.setErrorPage("/accessDenied");
		CustomExceptionTranslationFilter eTranslationFilter=new CustomExceptionTranslationFilter(customAuthenticationEntryPoint());
		eTranslationFilter.setAccessDeniedHandler(accessDeniedHandlerImpl);
		return eTranslationFilter;
	}
	
	
	 @Bean
	  public AuthenticationEntryPoint customAuthenticationEntryPoint() {
	      return new LoginUrlAuthenticationEntryPoint("/login");
	 }
	 
	
	// Delegates functionality to SecurityContextRepository
	@Bean
	CustomSecurityContextPersistenceFilter securityContextPersistenceFilter(){
		SecurityContextRepository sCRepo=customHttpSessionSecurityContextRepository();
		((HttpSessionSecurityContextRepository) sCRepo).setAllowSessionCreation(true); //by default true
		CustomSecurityContextPersistenceFilter sCPFilter=new CustomSecurityContextPersistenceFilter(sCRepo);
		return sCPFilter;
	}
	
	@Bean
	  public HttpSessionSecurityContextRepository customHttpSessionSecurityContextRepository() {
	      return new HttpSessionSecurityContextRepository();
	 }
	 
	 
}
