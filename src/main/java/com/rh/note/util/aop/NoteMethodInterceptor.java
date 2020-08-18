package com.rh.note.util.aop;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 笔记拦截器
 * 用于汇总所有的方法连接器
 */
@RequiredArgsConstructor
public class NoteMethodInterceptor implements InvocationHandler {

    /**
     * 被代理类
     */
    @NonNull
    private Object target;
    /**
     * 拦截器链
     */
    @NonNull
    private Map<Class<Annotation>, INoteMethodInterceptor> map;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (MapUtils.isEmpty(map)) {
            return method.invoke(target, args);
        }
        List<INoteMethodInterceptor> interceptors = map.entrySet().stream()
                .filter(entry -> ProxyUtil.match(entry.getKey(), method.getDeclaringClass()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        MethodInterceptorParam param = new MethodInterceptorParam()
                .setMethod(method)
                .setArgs(args)
                .setFunction(arr -> method.invoke(target, arr))
                .setIterator(interceptors.iterator());
        return param.getResult();
    }
}
