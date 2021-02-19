package com.rh.note.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志捕获 切面
 */
@Slf4j
@Aspect
@Component
public class CatchLoggerAspect {

    @Pointcut("execution(* com.rh.note.event.*Action.*(..))")
    public void catchActionException() {}

    @Around("catchActionException()")
    public Object recordErrorLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("[捕获Action异常]", e);
            throw e;
        }
    }
}
