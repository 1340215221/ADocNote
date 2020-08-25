package com.rh.note.aspect;

import com.rh.note.util.aop.INoteMethodInterceptor;
import com.rh.note.util.aop.MethodInterceptorParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志调用方法拦截器
 */
@Slf4j
public class DoActionLogInterceptor implements INoteMethodInterceptor {
    @Override
    public Object doResult(@NonNull MethodInterceptorParam param) throws Throwable {
        log.info("{}.{}() => [{}]", param.getClassName(), param.getMethodName(), param.getAnnotation(DoActionLog.class).value());
        return param.getResult();
    }
}
