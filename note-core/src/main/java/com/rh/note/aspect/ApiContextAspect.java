package com.rh.note.aspect;

import com.rh.note.annotation.ProManageContext;
import com.rh.note.annotation.WorkContext;
import com.rh.note.api.ProManageLoaderApi;
import com.rh.note.api.WorkLoaderApi;
import com.rh.note.util.ViewContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * api设置容器切面
 */
@Aspect
@Component
@Configuration
public class ApiContextAspect {

    @Autowired
    private ProManageLoaderApi proManageLoaderApi;
    @Autowired
    private WorkLoaderApi workLoaderApi;

    @Pointcut("execution(* com.rh.note.api..*ViewApi.*(..))")
    public void proManageContext() {}

    @Around(value = "proManageContext()")
    public Object handleProManageContext(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        ProManageContext pmc = targetClass.getDeclaredAnnotation(ProManageContext.class);
        ApplicationContext app = null;
        if (pmc != null) {
            app = proManageLoaderApi.getApp();
        }
        WorkContext wc = targetClass.getDeclaredAnnotation(WorkContext.class);
        if (wc != null) {
            app = workLoaderApi.getApp();
        }
        // 前置操作
        ViewContextUtil.context.set(app);
        // 执行方法
        Object result = joinPoint.proceed();
        // 后置操作
        ViewContextUtil.context.set(null);
        return result;
    }

}
