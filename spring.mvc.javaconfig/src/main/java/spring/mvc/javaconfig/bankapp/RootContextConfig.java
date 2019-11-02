package spring.mvc.javaconfig.bankapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "spring.mvc.javaconfig.bankapp.domain",
		"spring.mvc.javaconfig.bankapp.dao", "spring.mvc.javaconfig.bankapp.service" })
public class RootContextConfig {

}
