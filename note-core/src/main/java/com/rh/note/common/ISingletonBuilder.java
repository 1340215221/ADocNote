package com.rh.note.common;

import groovy.lang.Closure;

import javax.annotation.PreDestroy;

public interface ISingletonBuilder {
    /**
     * 初始化
     * 参与swing控件的组装
     */
    void init(Closure children);
    /**
     * 销毁方法
     */
    @PreDestroy
    void destroy();
}
