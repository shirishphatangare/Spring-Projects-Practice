package com.spring.soap.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.in28minutes.courses.GetCourseDetailsRequest;
import com.in28minutes.courses.GetCourseDetailsResponse;
import com.spring.soap.consumer.service.CourseService;
import com.spring.soap.consumer.util.CourseConstants;
import com.spring.soap.consumer.util.Util;

/*For @RefreshScope, Spring Cloud dependency is required
A Scope implementation that allows for beans to be refreshed dynamically at runtime (see refresh(String) and refreshAll()). If a bean is refreshed then the next time the bean is accessed (i.e. a method is executed) a new instance is created. All lifecycle methods are applied to the bean instances, so any destruction callbacks that were registered in the bean factory are called when it is refreshed, and then the initialization callbacks are invoked as normal when the new instance is created. A new bean instance is created from the original bean definition, so any externalized content (property placeholders or expressions in string literals) is re-evaluated when it is created.
Note that all beans in this scope are only initialized when first accessed, so the scope forces lazy initialization semantics. The implementation involves creating a proxy for every bean in the scope
*/
// 1) Bean is refreshed dynamically at runtime. Every time the bean/component is accessed (i.e. a method is executed) a new instance is created.
// 2) Lazy Initialization of bean or component
// 3) Config properties take latest values every time 
// 4) Proxy of a bean or Component is created
// 5) Since a proxy is created, it is not a good idea to use it at component level for property values refresh. A model class can be created for that purpose and let it be proxied.  


@RefreshScope
@Component
public class CourseProcess {
	
	 private static final Logger log = LoggerFactory.getLogger(CourseProcess.class);
	 
	 @Value("${course.appName}")
	 private String appName;
	 
	 @Autowired
	 private CourseService courseService;

	 @Scheduled(cron="${course.schedule}")
	 public void process() {

	        String jobStartedDt = Util.getCurrentDateTime();

	        log.info("{} Course Service started at {} ", CourseConstants.DASHES, jobStartedDt);

	        try {
	            // preparing SOAP request
	            GetCourseDetailsRequest request = courseService.prepareSoapRequest();
	            // fetch the data from the web service
	            GetCourseDetailsResponse response = (GetCourseDetailsResponse) courseService
	                        .loadCourseInformation(request);

	                log.info("Received Worker Response for id - {} ", response.getCourseDetails().getId());

	        } catch (Exception e) {
	            log.error("{} Course Service failed due to {} - {}", appName, e.getClass(), e.getMessage());
	            log.error(Util.getStackTrace(e));
	        } finally {
	        	String jobFinishedDt = Util.getCurrentDateTime();
	        	log.info("{} Course Service finished at {} ", CourseConstants.DASHES, jobFinishedDt);
	        }
	    }
	    
	
}
