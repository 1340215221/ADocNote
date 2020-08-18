package com.rh.note.util.aop;

import lombok.NonNull;

/**
 * 自定义拦截器
 */
public interface INoteMethodInterceptor {

    Object doResult(@NonNull MethodInterceptorParam param) throws Throwable;

}
