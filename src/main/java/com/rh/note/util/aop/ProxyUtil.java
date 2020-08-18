package com.rh.note.util.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 静态代理工具
 */
public class ProxyUtil {

    /**
     * 初始化
     */

    /**
     * 生成代理对象
     */
    public <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this.getHandler(clazz));
        Object bean = enhancer.create();
        if (bean == null || !bean.getClass().equals(clazz)) {
            return null;
        }
        return (T) bean;
    }

    /**
     * 获取方法拦截器, 通过class
     */
    private <T> MethodInterceptor getHandler(Class<T> clazz) {
        //todo
        return null;
    }

}
