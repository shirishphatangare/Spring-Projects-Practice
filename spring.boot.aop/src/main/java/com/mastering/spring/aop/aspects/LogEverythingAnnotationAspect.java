package com.mastering.spring.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LogEverythingAnnotationAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("com.mastering.spring.aop.config.JoinPointConfiguration.logEverythingAnnoation()")
    public Object logEverythingAroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    	logger.info("Starting @Around advice in {}",this.getClass().getSimpleName());
        logger.info("Method {} started execution", proceedingJoinPoint.getSignature());
        logger.info("Method {} arguments are {}", proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs());
        Object retVal = proceedingJoinPoint.proceed();
        logger.info("Method {} completed execution ", proceedingJoinPoint.getSignature());
        logger.info("Ending @Around advice in {}", this.getClass().getSimpleName());
        return retVal;
    }

}