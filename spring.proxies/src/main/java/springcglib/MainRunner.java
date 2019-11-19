package springcglib;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRunner {
	
	
	public static void main(String args[]) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ApplicationConfig.class);
		context.refresh();
		context.registerShutdownHook();
	}
	

}
