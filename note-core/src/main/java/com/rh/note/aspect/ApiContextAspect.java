package com.rh.note.aspect;

import com.rh.note.annotation.ComponentBean;
import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiContextAspect {

    @Pointcut("execution(* com.rh.note.api..*ViewApi.*(..))")
    public void proManageContext() {}

    @Around(value = "proManageContext()")
    public Object handleProManageContext(ProceedingJoinPoint joinPoint) throws Throwable {
        beforeProceed(joinPoint);
        return joinPoint.proceed();
    }

    /**
     * 前置操作
     */
    private void beforeProceed(@NonNull ProceedingJoinPoint joinPoint) {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        ComponentBean annotation = targetClass.getDeclaredAnnotation(ComponentBean.class);
    }
}
