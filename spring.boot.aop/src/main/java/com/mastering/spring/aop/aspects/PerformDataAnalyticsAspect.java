package com.mastering.spring.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class PerformDataAnalyticsAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("com.mastering.spring.aop.config.JoinPointConfiguration.allmethodsinClassNameEndingDaoExecution()")
    public Object calculateMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		logger.info("Starting @Around advice in {}", this.getClass().getSimpleName());
        long start = System.currentTimeMillis();
        Object retVal = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        logger.info("Method {} took {} ms to execute", proceedingJoinPoint.getSignature(), duration);
        logger.info("Ending @Around advice in {}", this.getClass().getSimpleName());
        return retVal;
    }

}
