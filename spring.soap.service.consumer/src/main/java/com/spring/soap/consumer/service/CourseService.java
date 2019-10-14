package com.spring.soap.consumer.service;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.in28minutes.courses.GetCourseDetailsRequest;
import com.in28minutes.courses.GetCourseDetailsResponse;
import com.in28minutes.courses.ObjectFactory;
import com.spring.soap.consumer.util.CourseConstants;

@Service
public class CourseService {
	 private static final Logger log = LoggerFactory.getLogger(CourseService.class);
	 
	 @Value("${course.service.url}")
	 private String courseServiceUrl;
	
	 @Value("${course.appName}")
	 private String appName;
	    
	 @Value("${course.serviceHit.maxAttempts}")
	 private String maximumRetryAttempts;
	 
	 @Autowired
	 @Qualifier("Course")
	 private WebServiceTemplate courseWebServiceTemplate;
	 
	 // Prepare SOAP Request
	 public GetCourseDetailsRequest prepareSoapRequest() {
		 ObjectFactory objectFactory = new ObjectFactory();
		 GetCourseDetailsRequest request = objectFactory.createGetCourseDetailsRequest();
		 request.setId(2);
	     return request;
	 }
		    
		    
	// Invoke SOAP URL with request to get the response
	@Retryable(include = {SocketTimeoutException.class, UnknownHostException.class}, maxAttemptsExpression = "#{${course.serviceHit.maxAttempts}}", backoff = @Backoff(delay = 3000))
	public GetCourseDetailsResponse loadCourseInformation(GetCourseDetailsRequest request){
		GetCourseDetailsResponse jaxBElement2 = (GetCourseDetailsResponse) courseWebServiceTemplate
	            .marshalSendAndReceive(courseServiceUrl, request);
	    return jaxBElement2;
	}
	
	@Recover
	public Object recover(WebServiceIOException e) throws WebServiceIOException {
	    log.warn(CourseConstants.COURSE_EXCEPTION_MSG + " - Maximum Retry attempts " + maximumRetryAttempts + " Exhausted");
	    throw e;
	}
	
	@Recover
	public Object recover(UnknownHostException e) throws UnknownHostException {
	    log.warn(CourseConstants.COURSE_EXCEPTION_MSG + " - Maximum Retry attempts " + maximumRetryAttempts + " Exhausted");
	    throw e;
	}
	
	@Recover
	  public Object recover(Exception e) throws Exception {
		log.warn(CourseConstants.COURSE_EXCEPTION_MSG + " - Course Service Retry failure");
	    throw e;
	  }
}
