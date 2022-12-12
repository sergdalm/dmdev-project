package com.sergdalm.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Around("isServiceLayer()")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Logging before - in class {} invoked method {} in class , with arguments {}", joinPoint.getTarget(), joinPoint.getSignature().getName(), joinPoint.getArgs());
        try {
            Object result = joinPoint.proceed();
            log.info("Logging after returning - in class {} invoked method {}, result {}", joinPoint.getTarget(), joinPoint.getSignature().getName(), result);
            return result;
        } catch (Throwable ex) {
            log.info("Logging after throwing - in class {} invoked method {}, exception {}: {}", joinPoint.getTarget(), joinPoint.getSignature().getName(), ex.getClass(), ex.getMessage());
            throw ex;
        } finally {
            log.info("Logging after (finally) - in class {} invoked method {}", joinPoint.getTarget(), joinPoint.getSignature());
        }
    }
}
