package com.rh.note.util.aop;

public interface IFunction {

    /**
     * 执行被代理方法, 传入方法参数
     */
    Object invoke(Object[] args) throws Throwable;

}
