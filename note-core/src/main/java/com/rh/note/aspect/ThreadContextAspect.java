package com.rh.note.aspect;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.bean.ThreadContextBean;
import com.rh.note.common.ViewThreadContext;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 将event中获取到的当前容器, 设置到当前线程中
 */
@Slf4j
@Aspect
@ComponentBean(FrameCategoryEnum.UNIVERSAL)
public class ThreadContextAspect {

    @Autowired
    private ConfigurableApplicationContext context;

    @Pointcut("execution(* com.rh.note.event.*Event.*(..))")
    public void executionEvent() {}

    @Around("executionEvent()")
    public Object handleThreadContext(ProceedingJoinPoint joinPoint) throws Throwable {
        ThreadContextBean bean = beforeProceed();
        try {
            return joinPoint.proceed();
        } finally {
            afterProceed(bean);
        }
    }

    /**
     * 后置操作
     */
    private void afterProceed(@NotNull ThreadContextBean bean) {
        if (bean.decrement() < 1) {
            ViewThreadContext.clearThreadContext();
        }
    }

    /**
     * 前置操作
     */
    private @NotNull ThreadContextBean beforeProceed() {
        ThreadContextBean bean = ViewThreadContext.getThreadContextBean();
        if (bean == null) {
            ThreadContextBean newBean = new ThreadContextBean(this.context);
            ViewThreadContext.setThreadContextBean(newBean);
            return newBean;
        }
        if (bean.getContext() != this.context) {
            throw new ApplicationException(ErrorCodeEnum.NOT_ALLOWED_TO_OPERATE_TWO_CONTAINER_CONTROLS_IN_ONE_THREAD);
        }
        bean.increment();
        return bean;
    }
}
