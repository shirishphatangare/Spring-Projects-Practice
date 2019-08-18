package com.demo.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.config.RepositoryConfig;
import com.demo.config.SecurityConfig;
import com.demo.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityConfig.class,RepositoryConfig.class,WebConfig.class}) // All these contexts - web,security and repository will be autowired into WebApplicationContext 
@WebAppConfiguration
@Sql({"/security_schema.sql"})
//@Sql({"classpath:security_schema.sql"})
public class ReqBuilderAndPostProcessors {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .defaultRequest(get("/").with(user("anyUser").roles("anyRole"))) // user is SecurityMockMvcRequestPostProcessors
                .build();
    }
    
    @Test
    public void getUpdatePage() throws Exception { // Test succeeds as user and password are correct
        mvc.perform(
        		post("/updatePage") // post must accompany with csrf
        		.with(csrf())  // csrf is SecurityMockMvcRequestPostProcessors
        		.param("id", "1")
        		.with(httpBasic("admin","adminPassword")) // httpBasic is SecurityMockMvcRequestPostProcessors
        		)
        .andExpect((status().isOk()));
    }
      
	@Test
    public void getHome() throws Exception { //Needs authentication but we are not passing required credentials hence test fails
    	mvc.perform(get("/").with(anonymous())) // anonymous is SecurityMockMvcRequestPostProcessors
    	.andExpect((status().isUnauthorized()));
    }  
    
    @Test
    public void performLoginDefault() throws Exception { // Test pass since we are not passign credentials and expect failure
    	mvc.perform(formLogin()).andExpect((redirectedUrl("/login?error"))); // formlogin is a static import from SecurityMockMvcRequestBuilders
    }
        
    @Test
    public void postWithInvalidCsrf() throws Exception { // Test will pass since we are passing invalid csrf token
        mvc
            .perform(post("/logout").with(csrf().useInvalidToken()))
            .andExpect((status().isForbidden()));
    }
    
    @Test
    public void performLoginWithUserPassword() throws Exception { // Test will pass since correct credentials are passed to form login page
    	mvc.perform(formLogin("/login").user("admin").password("adminPassword"))
    	.andExpect((redirectedUrl("/")));
    }
    
    @Test
    public void performLoginWithParameterSet() throws Exception {// Test fails since credentials are correct and we are expecting a failure
    	mvc.perform(formLogin("/login").user("username","employee").password("password","employeePassword"))
    	.andExpect((redirectedUrl("/login?error")));
    }
    
    @Test
    public void performLogout() throws Exception { // Test pass since logout don't need credentials
        mvc.perform(logout("/logout"))  // logout is a static import from SecurityMockMvcRequestBuilders
        .andExpect((redirectedUrl("/login?logout")));
    }  
}
