package com.rh.note.aspect;

import com.rh.note.util.aop.INoteMethodInterceptor;
import com.rh.note.util.aop.MethodInterceptorParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志调用方法拦截器
 */
@Slf4j
public class DoActionLogInterceptorImpl implements INoteMethodInterceptor {
    @Override
    public Object doResult(@NonNull MethodInterceptorParam param) throws Throwable {
        //--处理逻辑
        Object result = param.getResult();
        //--处理逻辑
        return null;
    }
}
