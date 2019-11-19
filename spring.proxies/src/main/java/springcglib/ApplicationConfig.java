package springcglib;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration

public class ApplicationConfig {
	
	@Bean("springBean")
	SpringBean springBean() {
		System.out.println("Creating Spring Bean in " + getClass().getSimpleName());
		// You will see output as Creating Spring Bean in ApplicationConfig$$EnhancerBySpringCGLIB$$1e2f9451. It indicates spring uses CGLIB proxy to create a singleton bean
		return new SpringBean();
	}

}
