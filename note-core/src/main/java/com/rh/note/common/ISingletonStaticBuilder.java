package com.rh.note.common;

import groovy.lang.Closure;

public interface ISingletonStaticBuilder extends ISingletonBuilder {
    /**
     * 初始化
     * 参与swing控件的组装
     */
    void init(Closure children);
}
