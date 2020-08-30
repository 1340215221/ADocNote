package com.rh.note.config;

import com.rh.note.aspect.DoActionLog;
import com.rh.note.aspect.DoActionLogInterceptor;
import com.rh.note.aspect.GlobalExceptionHandler;
import com.rh.note.aspect.GlobalExceptionInterceptor;
import com.rh.note.aspect.GlobalResultHandler;
import com.rh.note.aspect.GlobalResultInterceptor;
import com.rh.note.util.aop.INoteMethodInterceptor;

import java.lang.annotation.Annotation;

/**
 * 注册aop类型
 * 注解和对应的拦截器
 */
public enum AOPConfigEnum {

    /**
     * action全局错误弹窗提示处理
     */
    action_exception() {
        @Override
        public Class<GlobalExceptionHandler> getAnnotationClass() {
            return GlobalExceptionHandler.class;
        }

        @Override
        public INoteMethodInterceptor getInterceptor() {
            return new GlobalExceptionInterceptor();
        }
    },
    /**
     * action调用日志
     */
    do_action() {
        @Override
        public Class<DoActionLog> getAnnotationClass() {
            return DoActionLog.class;
        }

        @Override
        public INoteMethodInterceptor getInterceptor() {
            return new DoActionLogInterceptor();
        }
    },
    /**
     * 全局结果处理
     */
    result_handle() {
        @Override
        public INoteMethodInterceptor getInterceptor() {
            return new GlobalResultInterceptor();
        }

        @Override
        public Class<GlobalResultHandler> getAnnotationClass() {
            return GlobalResultHandler.class;
        }
    },
    ;

    /**
     * 获得方法拦截器实例
     */
    public abstract INoteMethodInterceptor getInterceptor();

    /**
     * 获得注解类型
     */
    public abstract <T extends Annotation> Class<T> getAnnotationClass();
}
