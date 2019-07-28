package com.example.springsecuritybasics.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // A convenience annotation that is itself annotated with @Controller (For Component scan) and @ResponseBody.
                 // Types that carry this annotation are treated as controllers where @RequestMapping methods assume @ResponseBody semantics by default.
public class TimeController {

	// Annotation for mapping HTTP GET requests onto specific handler methods.
	// Specifically, @GetMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).
	
	// Both @PreAuthorize and @PostAuthorize annotations provide expression-based access control. 
	// Hence, predicates can be written using SpEL (Spring Expression Language).

	 @GetMapping(value = "/publicTime", produces = "application/json")
	 public ResponseEntity<?> currentTime(HttpServletRequest httpServletRequest){
		 if (httpServletRequest.isUserInRole("ROLE_USER")) { // Alternative to using @PreAuthorize annotation
			 return new ResponseEntity<>(ZonedDateTime.now(), HttpStatus.OK);
		 }else {
		      return new ResponseEntity<>("user unauthorized", HttpStatus.UNAUTHORIZED);
		 }
	  }

	  @PreAuthorize("hasRole('ROLE_ADMIN')")
	  @GetMapping(value = "/secretTime", produces = "application/json")
	  public ResponseEntity<?> currentSecretTime(){
	    return new ResponseEntity<>(ZonedDateTime.now(ZoneId.of("UTC")), HttpStatus.OK);
	  }
	  
	  
	  @GetMapping(value = "/publicIST", produces = "application/json") 
	  public ResponseEntity<?> currentIndianTime(){
	    return new ResponseEntity<>(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")), HttpStatus.OK);
	  }

}
