package com.mastering.spring.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


// This configuration ensures that PointCuts are specified at one location and can easily be maintained.

@Aspect
public class JoinPointConfiguration {
	
	@Pointcut("execution(* com.mastering.spring.aop.repo.*Dao.*(..))")
    public void allmethodsinClassNameEndingDaoExecution() {}

    @Pointcut("execution(public com.mastering.spring.aop.model.Stock com.mastering.spring.aop.repo.StockDao.retrieveStock())")
    public void retrieveStockInStockDaoExecution() {}

    @Pointcut("execution(public void com.mastering.spring.aop.service.ShoppingService.checkAndPlaceOrder())")
    public void checkAndPlaceOrderInShoppingServiceExecution() {}
    
    @Pointcut("@annotation(com.mastering.spring.aop.annotations.LogEverything)") // Notice that instead of execution, we are using @annotation in the PointCut
    public void logEverythingAnnoation() {}


}
