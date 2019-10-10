package com.spring.boot.restController.config;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.boot.restController.exception.GenericExceptionResponse;
import com.spring.boot.restController.exception.ResourceNotFoundException;


/* ResponseEntityExceptionHandler is a convenient base class for {@link ControllerAdvice @ControllerAdvice} classes
   that wish to provide centralized exception handling across all {@code @RequestMapping} methods through {@code @ExceptionHandler} methods.
*/

/* It would be great if we could create a centralized point for exceptions, so that whenever an exception is thrown by 
  any controller, it would be caught and handled by that point instead of having handler 
  methods in each controller class. @ControllerAdvice came into play.
  One controller advice class per application.
  You can think of @ControllerAdvice as an interceptor of exceptions thrown by methods annotated with @RequestMapping or one of the shortcuts.
*/

// @RestControllerAdvice - A convenience annotation that is itself annotated with @ControllerAdvice and @ResponseBody. It can be used
// here instead of using 2 separate annotations - @ControllerAdvice and @RestController

@ControllerAdvice
@RestController // CustomizedResponseEntityExceptionHandler itself is a Controller which provides response back with @ResponseBody
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	//@ExceptionHandler will handle all exceptions declared in it and then will delegate to a specific handler method
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		GenericExceptionResponse exceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class) // Add one method handler per exception
	public final ResponseEntity<Object> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		GenericExceptionResponse exceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false)); // Get a short description of this request, typically containing request URI and session id.
		 										// boolean parameter includeClientInfo whether to include client-specific information or not.
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		GenericExceptionResponse exceptionResponse = new GenericExceptionResponse(new Date(), "Validation Failed",
				ex.getBindingResult().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}	
}
