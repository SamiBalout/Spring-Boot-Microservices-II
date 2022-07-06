package com.stackroute.newz.aspect;

import java.io.File;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */

@Aspect
@Component
public class LoggerAspect {

	/*
	 * Write loggers for each of the methods of NewsController, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */

final static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);
	
	public String createLogFile() {
	ClassLoader classLoader = getClass().getClassLoader();
	File logFile = new File(classLoader.getResource("logback.xml").getFile());
	
		if(logFile != null) {
			return "You have to create logback.xml file in resources folder";
		}
		return null;
		
	}
	
	@Before(value = "execution(* com.stackroute.newzcontroller.NewsController.*())")
	public void beforeControllerLogginAspect(JoinPoint jpt) {
		logger.info("Before Advice is working for controller layer - " +  jpt.getSignature().getName());
				
	}
	
	@After(value = "execution(* com.cgi.controller.BlogController.saveBlog(..)) and args(x,y,z,a)")
	public void afterControllerLogginAspectForSaveBlog(JoinPoint jpt,int  x,String y,String z,String a) {
		logger.info("After Advice is working for controller layer - " +  jpt.getSignature().getName());
		
				
	}

	@AfterThrowing(value = "NewzController()")
	public void afterThrowingControllerLogginAspect(JoinPoint jpt) {
		logger.info("Exceeption Occurs " +  jpt.getSignature().getName());
		
				
	}


}
