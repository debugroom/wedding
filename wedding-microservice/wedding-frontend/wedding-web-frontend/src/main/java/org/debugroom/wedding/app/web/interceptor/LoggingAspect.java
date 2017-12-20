package org.debugroom.wedding.app.web.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Pointcut("execution(* org.debugroom.wedding.app.web..*Controller.*(..))")
	public void auditLogPointCut(){
	}
	
	@Pointcut("execution(* org.debugroom.wedding.app.web..*Controller.*(..))")
	public void traceLogPointCut(){
		
	}
	

}
