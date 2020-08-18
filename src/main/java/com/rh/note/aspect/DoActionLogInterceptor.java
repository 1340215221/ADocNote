package com.rh.note.aspect;

import com.rh.note.util.aop.IAdocMethodInterceptor;
import com.rh.note.util.aop.MethodInterceptorParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志调用方法拦截器
 */
@Slf4j
public class DoActionLogInterceptor implements IAdocMethodInterceptor<DoActionLog> {
    @Override
    public Object apply(@NonNull MethodInterceptorParam<DoActionLog> param) throws Throwable {
        log.info("{}.{}, doAction-[{}]", param.getClassName(), param.getMethodName(), param.getAnnotation().value());
        return param.getResult();
    }
}
