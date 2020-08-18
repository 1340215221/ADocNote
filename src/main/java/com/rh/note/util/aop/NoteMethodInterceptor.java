package com.rh.note.util.aop;

import com.rh.note.config.AOPConfigEnum;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 笔记拦截器
 * 用于汇总所有的方法连接器
 */
public class NoteMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object bean, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        List<INoteMethodInterceptor> interceptors = Arrays.stream(AOPConfigEnum.values())
                .filter(e -> ProxyUtil.match(e.getAnnotationClass(), method))
                .map(AOPConfigEnum::getInterceptor)
                .collect(Collectors.toList());
        MethodInterceptorParam param = new MethodInterceptorParam()
                .setMethod(method)
                .setArgs(args)
                .setFunction(arr -> methodProxy.invokeSuper(bean, arr))
                .setIterator(interceptors.iterator());
        return param.getResult();
    }
}
