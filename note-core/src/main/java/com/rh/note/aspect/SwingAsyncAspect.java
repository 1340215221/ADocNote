package com.rh.note.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * swing异步执行 切面
 */
@Slf4j
@Aspect
@Component
public class SwingAsyncAspect {

    @Pointcut("@annotation(com.rh.note.annotation.SwingAsync)")
    public void swingAsync() {
    }

    @Around("swingAsync()")
    public Object handleSwingAsync(ProceedingJoinPoint joinPoint) throws Throwable {
        SwingUtilities.invokeLater(() -> {
            try {
                joinPoint.proceed();
            } catch (Throwable e) {
                log.error("[swing异步执行 切面] error", e);
            }
        });
        return null;
    }
}
