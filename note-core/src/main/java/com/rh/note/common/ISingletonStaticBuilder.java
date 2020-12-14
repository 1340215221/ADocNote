package com.rh.note.common;

import groovy.lang.Closure;

import javax.annotation.PostConstruct;

public interface ISingletonStaticBuilder extends ISingletonBuilder {
    /**
     * 初始化
     * 参与swing控件的组装
     */
    @PostConstruct
    void init(Closure children);
}
