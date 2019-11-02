package spring.mvc.javaconfig.bankapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/* It configures objects that are required for developing Spring Web MVC applications. To override default configurations,
implement the WebMvcConfigurer interface class that defines default methods for different configurations. */

@EnableWebMvc
@Configuration
@ComponentScan("spring.mvc.javaconfig.bankapp.web")
public class WebContextConfig implements WebMvcConfigurer  {
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}
	
}
