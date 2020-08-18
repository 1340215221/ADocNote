package com.rh.note.util.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 处理拦截器链
 */
public interface IAdocMethodInterceptor extends MethodInterceptor {

    @Override
    default Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    }

    Object apply();
}
