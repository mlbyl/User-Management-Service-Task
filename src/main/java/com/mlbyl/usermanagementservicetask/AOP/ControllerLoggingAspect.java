package com.mlbyl.usermanagementservicetask.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {

    @Around("execution(* com.mlbyl.usermanagementservicetask.controller.*.*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Request logging
        log.info("Controller method called: {}.{}", className, methodName);
        log.debug("Arguments: {}", Arrays.toString(joinPoint.getArgs()));

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();  // Metodu çalıştır

            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Controller method executed successfully: {}.{} - Execution time: {}ms",
                    className, methodName, executionTime);

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Controller method failed: {}.{} - Execution time: {}ms",
                    className, methodName, executionTime);
            throw e;  // Exception'ı tekrar fırlat
        }
    }
}