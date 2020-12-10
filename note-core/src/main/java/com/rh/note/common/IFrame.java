package com.rh.note.common;

/**
 * 窗口基础接口
 */
public interface IFrame {

    void init();

    void registerComponent();

    void globalSettings();

    default void start() {
        globalSettings();
        registerComponent();
        init();
    }

}