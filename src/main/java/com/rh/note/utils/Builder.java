package com.rh.note.utils;

import com.rh.note.factory.SwingBuilderFactroy;
import groovy.swing.SwingBuilder;

/**
 * 构建视图模块
 */
public interface Builder extends IInit {
    SwingBuilder swingBuilder = SwingBuilderFactroy.swingBuilder;

    /**
     * 初始化方法
     */
    void init();

    /**
     * 获得swingBuilder中存储的id
     */
    String getId();

}
