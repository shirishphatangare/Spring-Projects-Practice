package com.spring.soap.consumer.config;

import org.apache.wss4j.dom.WSConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.ClientHttpRequestMessageSender;

@Configuration
public class SoapClientConfigurations {

	
	private static final String COURSES_SCHEMA_CONTEXT = "com.in28minutes.courses";
	private static final String SECUREMENTACTIONS = "Timestamp UsernameToken";
	
	@Value("${course.user}")
	private String user;

	@Value("${course.password}")
	private String password;

	@Value("${course.service.readTimeout}")
	private int connectionReadTimeout;

	@Value("${course.service.connectionTimeout}")
	private int connectionTimeout;


	@Bean(name = "Course") // In case of 2 WebServiceTemplate beans qualifier with name helps to resolve conflict
	public WebServiceTemplate webServiceTemplateHR() {
		return webServiceTemplate(COURSES_SCHEMA_CONTEXT);
	}


	/*
	 * Creating the web service template
	 */
	private WebServiceTemplate webServiceTemplate(String contextPath) {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(contextPath); // Set a JAXB context path. The context path is a list of colon (:) separated Java package names that contain schema derived classes.

		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);

		ClientInterceptor[] interceptors = new ClientInterceptor[] { securityInterceptor(), new LogMessageTracingClientInterceptor() };
		webServiceTemplate.setInterceptors(interceptors);

		webServiceTemplate.setMessageSender(webServiceMessageSender());
		//webServiceTemplate.setMessageFactory(messageFactory()); disabled AxiomSoapMessageFactory - Using default SaajSoapMessageFactory

		return webServiceTemplate;

	}
	
	
	// By default webServiceTemplate use SaajSoapMessageFactory
	
	/* payloadCaching=false instructs Axiom not to build the object model tree for the payload. This is what enables the performance gain, but it also implies that the payload can only be accessed once.
	It triggers a NodeUnavailableException (OMException in older versions) when the payload is consumed by the trace logging.
 	*/	
	@Bean
    public AxiomSoapMessageFactory messageFactory() {
		AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_11);
        // This setting disables response payload caching which helped to resolve issue of Java heap OutOfMemoryException due to large attachments in SOAP response
        messageFactory.setPayloadCaching(false);
        return messageFactory;
    }

	/**
	 * Security configurations for the web service template
	 * 
	 * @return
	 */
	@Bean
	public Wss4jSecurityInterceptor securityInterceptor() {
		Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();

		wss4jSecurityInterceptor.setSecurementUsernameTokenCreated(false);
		wss4jSecurityInterceptor.setSecurementUsernameTokenNonce(false);
		wss4jSecurityInterceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
		wss4jSecurityInterceptor.setSecurementActions(SECUREMENTACTIONS);

		wss4jSecurityInterceptor.setSecurementUsername(user);
		wss4jSecurityInterceptor.setSecurementPassword(password);
		return wss4jSecurityInterceptor;
	}

	/**
	 * Connection configurations for web service template
	 * 
	 * @return
	 */
	private WebServiceMessageSender webServiceMessageSender() {
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setConnectTimeout(connectionTimeout);
		simpleClientHttpRequestFactory.setReadTimeout(connectionReadTimeout);
		return new ClientHttpRequestMessageSender(simpleClientHttpRequestFactory);
	}

}
