package com.rh.note.common;

/**
 * 窗口基础接口
 */
public interface ILoader {

    void init();

    void globalSettings();

    default void start() {
        globalSettings();
        init();
    }

}
