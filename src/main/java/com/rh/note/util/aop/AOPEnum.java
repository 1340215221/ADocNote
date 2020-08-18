package com.rh.note.util.aop;

import com.rh.note.aspect.DoActionLog;
import com.rh.note.aspect.DoActionLogInterceptor;

/**
 * 注册aop类型
 * 注解和对应的拦截器
 */
public enum AOPEnum {

    /**
     * action调用日志
     */
    do_action() {
        @Override
        public boolean match(Class clazz) {
            if (clazz == null) {
                return false;
            }
            //获得class的类上注解和方法注解, 如果存在匹配的则执行
            return ProxyUtil.matchAnnotationOnClass(clazz, DoActionLog.class)
                    || ProxyUtil.matchAnnotationOnMethod(clazz, DoActionLog.class);
        }

        @Override
        public IAdocMethodInterceptor getInterceptor() {
            return new DoActionLogInterceptor();
        }
    },
    ;

    /**
     * 匹配注解类型
     */
    public abstract boolean match(Class clazz);

    /**
     * 获得方法拦截器实例
     */
    public abstract IAdocMethodInterceptor getInterceptor();

}
