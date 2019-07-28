package com.example.springsecuritybasics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) // Exclude DataSourceAutoConfiguration 
// This fixed - “Failed to Configure a DataSource” Error
// Do not know why DataSource Auto-Configuration is required or enabled for Simple REST application.
public class SpringSecurityBasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBasicsApplication.class, args);
	}

}
