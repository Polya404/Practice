package org.application.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLog {

    @Pointcut("@annotation(org.application.aspect.Log)")
    public void logPointCut(){}

//    @Before("logPointCut()")
//    public void lofPointcut(){
//        System.out.println("Log with pointcut");
//    }

    @Around("logPointCut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Aspect log");
        return joinPoint.proceed();
    }
}
