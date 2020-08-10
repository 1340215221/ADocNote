package com.rh.note.util;

import groovy.lang.Closure;

/**
 * 组件
 */
public interface SwingComponent extends ISwingBuilder {

    /**
     * 初始化组件
     */
    void init(Closure children);
    /**
     * 获得id
     */
    static String getId() {
        return "";
    }

}