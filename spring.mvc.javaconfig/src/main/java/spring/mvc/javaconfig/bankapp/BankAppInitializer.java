package spring.mvc.javaconfig.bankapp;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*Java Configuration - This is all you need to do to create a Spring Web MVC application that doesn’t use web.xml, application context XML
and spring-servlet.xml files .*/


public class BankAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/*AbstractAnnotationConfigDispatcherServletInitializer supplies the root web application context to an instance
	of ContextLoaderListener.*/
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootContextConfig.class };
	}

	/*AbstractAnnotationConfigDispatcherServletInitializer supplies the child web application context to an instance of
	DispatcherServlet.*/
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebContextConfig.class };
	}

	// The getServletMappings method specifies the servlet mappings for the DispatcherServlet
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
}


/* In XML config, root web application context XML (applicationContext.xml) is loaded by ContextLoaderListener and child web 
 * application context XML (spring-servlet.xml) is loaded by DispatcherServlet, and the web.xml configured DispatcherServlet 
 * and ContextLoaderListener */

/*@Configuration annotated RootContextConfig class that configures beans that belong to the root web application
context whereas @Configuration annotated WebContextConfig class that configures beans belonging to the web layer of the application*/

/*Instead of using a web.xml file, in Java config you can use Spring’s AbstractAnnotationConfigDispatcherServletInitializer class (an
implementation of Spring’s WebApplicationInitializer) to programmatically configure the ServletContext, and to
register a DispatcherServlet and a ContextLoaderListener with the ServletContext*/



