package com.rh.note.util.aop;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * 方法连接器参数
 */
@Setter(AccessLevel.PACKAGE)
public class MethodInterceptorParam {

    /**
     * 目标方法对象
     */
    private Method method;
    /**
     * 方法参数
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    private Object[] args;
    /**
     * 拦截器迭代器
     */
    @Getter(AccessLevel.NONE)
    private Iterator<INoteMethodInterceptor> iterator;
    /**
     * 执行目标方法
     */
    private IFunction function;

    public MethodInterceptorParam() {
    }

    /**
     * 获得方法名
     */
    public String getMethodName() {
        return method.getName();
    }

    /**
     * 获得类名
     */
    public String getClassName() {
        return method.getDeclaringClass().getSimpleName();
    }

    /**
     * 获取注解
     */
    public <T extends Annotation> T getAnnotation(@NonNull Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }

    /**
     * 获得返回值
     */
    public Object getResult() throws Throwable {
        return getResult(this.args);
    }

    /**
     * 获得返回值
     */
    public Object getResult(Object[] args) throws Throwable {
        this.args = args;
        if (iterator.hasNext()) {
            return iterator.next().doResult(this);
        }
        return function.invoke(this.args);
    }

}
