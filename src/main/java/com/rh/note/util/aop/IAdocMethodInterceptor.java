package com.rh.note.util.aop;

import lombok.NonNull;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * 处理拦截器链
 */
public interface IAdocMethodInterceptor<T extends Annotation> extends MethodInterceptor {

    @Override
    default Object intercept(Object bean, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //todo
        Class<T> annotationClass = getAnnotation();
        //判断目标类上是否加有该注解, 全部方法都走拦截apply方法, 否则直接执行并返回结果
        //判断目标方法上是否加有该注解, 所有带有该注解的方法才走apply方法, 否则直接执行并返回结果
        if (bean.getClass().getSuperclass().getAnnotation(annotationClass) == null && method.getAnnotation(annotationClass) == null) {
            return methodProxy.invokeSuper(bean, args);
        }

        MethodInterceptorParam<T> param = new MethodInterceptorParam<T>()
                .setClassName(method.getDeclaringClass().getSimpleName())
                .setMethodName(method.getName())
                .setAnnotation(method.getAnnotation(annotationClass))
                .setFunction(arr -> methodProxy.invokeSuper(bean, arr));
        return apply(param);
    }

    /**
     * 获得拦截器要处理的注解类型
     */
    default Class<T> getAnnotation() {
        return Arrays.stream(this.getClass().getGenericInterfaces()) // 获得到包括IAdocMethodInterceptor的数组. 当前拦截器实现的所有接口
                .map(e -> (ParameterizedType) e)
                .filter(e -> e.getRawType().equals(IAdocMethodInterceptor.class))
                .map(e -> e.getActualTypeArguments()[0]) // 获得接口泛型的实现, 已知IAdocMethodInterceptor只有一个泛型
                .findFirst()
                .map(e -> (Class<T>) e)
                .get();
    }

    /**
     * 切面业务处理方法
     */
    Object apply(@NonNull MethodInterceptorParam<T> param) throws Throwable;
}
