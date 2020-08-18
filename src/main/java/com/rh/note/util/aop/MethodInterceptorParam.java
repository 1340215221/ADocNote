package com.rh.note.util.aop;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * 方法连接器参数
 */
@Getter
public class MethodInterceptorParam<T> {

    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 注解
     */
    private T annotation;
    /**
     * 方法返回值
     */
    private Object result;
    /**
     * 方法参数
     */
    private Object[] args;
    /**
     * 方法操作
     */
    @Getter(AccessLevel.NONE)
    private Supplier<Object> supplier;

    public MethodInterceptorParam() {
    }

    public Object getResult() {
        if (supplier != null) {
            result = supplier.get();
            supplier = null;
        }
        return result;
    }

}
