package com.rh.note.util.common;

/**
 * 窗口工厂
 */
public interface FrameFactory {

    void init();

    void config();

    void show();

    void registerFactory();

    void globalSettings();

    default void start() {
        globalSettings();
        registerFactory();
        init();
        config();
        show();
    }

}
