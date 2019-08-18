package com.demo.test;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.config.SecurityConfig;



@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration defines class-level metadata that is used to determine how to load and configure an ApplicationContext for integration tests.
@ContextConfiguration(classes = SecurityConfig.class) // Spring Security context
//@WebAppConfiguration is a class-level annotation that is used to declare that the ApplicationContext loaded for an integration test should be a WebApplicationContext.
@WebAppConfiguration // @WebAppConfiguration must be used in conjunction with @ContextConfiguration
@Ignore //Ignoring because we haven't added any @Test method
public class SecurityMockMvc {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc; // Performs request operations - GET,POST,PUT,DELETE,UPDATE etc.

    // Integrate with WebApplicationContext, Spring Security context, springSecurityFilterChain and build the MockMvc object
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context) // If we don't want whole web application context, we can also do standalone setup
                .addFilters(springSecurityFilterChain)
                .build();
    }
}
