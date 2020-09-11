package com.rh.note.base;

/**
 * 窗口工厂
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
