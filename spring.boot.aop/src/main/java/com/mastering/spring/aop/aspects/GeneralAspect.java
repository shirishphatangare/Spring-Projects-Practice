package com.mastering.spring.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

// We use the @Aspect and @Configuration annotations to specify that this class contains Spring configuration for an aspect
@Aspect // Treat this class as Aspect
@Configuration
public class GeneralAspect { // An Aspect/class created for specific cross-cutting concern (logging) can contain all related Advices for that specific cross-cutting concern (logging)  

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
   // Single PointCut Declaration can be done here like this and reused in advices below 
   // @Pointcut("execution(* com.mastering.spring.aop.repo.*Dao.*(..))")
   // public void allmethodsinClassNameEndingDaoExecution() {}

	@Before("com.mastering.spring.aop.config.JoinPointConfiguration.allmethodsinClassNameEndingDaoExecution()")
	public void before(JoinPoint joinPoint) {
		logger.info("Starting @Before advice in {}",this.getClass().getSimpleName());
	    logger.info(" Before executing a method {}", joinPoint.getSignature());
	    logger.info(" Arguments passed are {}", joinPoint.getArgs());
	    logger.info("Ending @Before advice in {}",this.getClass().getSimpleName());
	}

	
	@After("com.mastering.spring.aop.config.JoinPointConfiguration.allmethodsinClassNameEndingDaoExecution()") // PointCut is an expression that identifies which method calls to intercept
	public void after(JoinPoint joinPoint) { // JoinPoint is the runtime result of AOP. A specific instance where the PointCut matches and advice is being executed is called JoinPoint.
		logger.info("Starting @After advice in {}",this.getClass().getSimpleName());
		logger.info(" After executing a method {}", joinPoint.getSignature());
		logger.info(" Arguments passed are {}", joinPoint.getArgs());
		logger.info("Ending @After advice in {}",this.getClass().getSimpleName());
	}
	
	// Give Fully qualified class name for return type like - com.mastering.spring.aop.model.Stock instead of Stock
	// @AfterReturning allows post-processing of result. It allows data formatting or enrich data before actual return
	@AfterReturning(value = "com.mastering.spring.aop.config.JoinPointConfiguration.retrieveStockInStockDaoExecution()", returning = "result") // returning = "result" to indicate the name of the variable to represent the return value from the method.
	    public void afterReturning(JoinPoint joinPoint, Object result) { // returning = "result" and Object result - Here result should match
			logger.info("Starting @AfterReturning advice in {}",this.getClass().getSimpleName());
	        logger.info(" After returning from a method {}", joinPoint.getSignature());
	        logger.info(" Arguments passed are {}", joinPoint.getArgs());
	        logger.info(" Value returned is {}", result);
	        logger.info("Ending @AfterReturning advice in {}",this.getClass().getSimpleName());
	    }
	
	
	// attribute is pointcut or value
	@AfterThrowing(pointcut = "com.mastering.spring.aop.config.JoinPointConfiguration.checkAndPlaceOrderInShoppingServiceExecution()", throwing = "exception")
	    public void afterThrowing(JoinPoint joinPoint, Throwable exception) { // throwing = "exception" and Throwable exception - Here exception should match
			logger.info("Starting @AfterThrowing advice in {}",this.getClass().getSimpleName());
			logger.info(" After throwing an exception from a method {}", joinPoint.getSignature());
	        logger.info(" Exception thrown is {}", exception.getClass());
	        logger.info("Ending @AfterThrowing advice in {}",this.getClass().getSimpleName());
	    }

}