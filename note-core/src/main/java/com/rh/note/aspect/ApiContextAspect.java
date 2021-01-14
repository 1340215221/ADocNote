package com.rh.note.aspect;

import com.rh.note.common.BaseView;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 将event中获取到的当前容器, 设置到当前线程中
 */
@Slf4j
@Aspect
@Component
public class ApiContextAspect {

    @Pointcut("execution(* com.rh.note.event.*Event.*(..))")
    public void executionEvent() {}

    @Around(value = "executionEvent()")
    public Object handleThreadContext(ProceedingJoinPoint joinPoint) throws Throwable {
        beforeProceed(joinPoint);
        Object result = joinPoint.proceed();
        afterProceed();
        return result;
    }

    /**
     * 后置操作
     */
    private void afterProceed() {
        BaseView.clearThreadContext();
    }

    /**
     * 前置操作
     */
    private void beforeProceed(@NonNull ProceedingJoinPoint joinPoint) {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        try {
            Field field = targetClass.getSuperclass().getDeclaredField("context");
            field.setAccessible(true);
            Object context = field.get(joinPoint.getTarget());
            BaseView.setThreadContext((ApplicationContext) context);
        } catch (Exception e) {
            log.error("[获取event所属容器]-[没有获取到当前容器] error, class={}, method={}", targetClass.getName(),
                    ((MethodSignature) joinPoint.getSignature()).getMethod().getName(), e);
        }
    }
}
