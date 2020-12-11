package com.rh.note.common;

import groovy.swing.SwingBuilder;

import javax.annotation.PostConstruct;

/**
 * swingBuilder控件工厂
 */
public interface ISwingBuilderFactory {
    /**
     * 注册控件
     */
    @PostConstruct
    void registerFactory();
}
