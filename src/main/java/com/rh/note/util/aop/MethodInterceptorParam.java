package com.rh.note.util.aop;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 方法连接器参数
 */
@Getter
@Setter(AccessLevel.PACKAGE)
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
    @Setter(AccessLevel.PUBLIC)
    private Object[] args;
    /**
     * 方法操作
     */
    @Getter(AccessLevel.NONE)
    private IFunction function;

    public MethodInterceptorParam() {
    }

    public Object getResult(Object[] args) throws Throwable {
        if (function != null) {
            result = function.invoke(args);
            function = null;
        }
        return result;
    }

    public Object getResult() throws Throwable {
        return this.getResult(args);
    }

}
