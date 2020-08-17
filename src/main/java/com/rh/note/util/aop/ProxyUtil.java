package com.rh.note.util.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 静态代理工具
 */
public class ProxyUtil {

    /**
     * 生成代理对象
     */
    public <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        Object bean = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this.getHandler(clazz));
        if (bean == null || !bean.getClass().equals(clazz)) {
            return null;
        }
        return (T) bean;
    }

    /**
     * 获取方法拦截器, 通过class
     */
    private <T> InvocationHandler getHandler(Class<T> clazz) {
        //todo
        return null;
    }

}
