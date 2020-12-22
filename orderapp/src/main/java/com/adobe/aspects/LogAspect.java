package com.adobe.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LogAspect {
	
	Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* com.adobe.prj.service.*.*(..))")
	public void doLogBefore(JoinPoint jp) {
		logger.info("Called :" + jp.getSignature());
		Object[] args = jp.getArgs();
		for(Object obj : args) {
			logger.info("argument : " + obj);
		}
	}
	 

	@After("execution(* com.adobe.prj.service.*.*(..))")
	public void doLogAfter(JoinPoint jp) {
		logger.info("******************");
	}
	
	@Around("execution(* com.adobe.prj.service.*.*(..))")
	public Object doProfile(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
			Object ret = pjp.proceed();
		long end = System.currentTimeMillis();
		logger.info("Time : " + (end-start) + "ms");
		return ret;
	}
	
}
