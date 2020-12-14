package com.rh.note.common;

import javax.annotation.PostConstruct;

/**
 * 窗口基础接口
 */
public interface ILoader {

    void init();

    void globalSettings();

    @PostConstruct
    default void start() {
        globalSettings();
        init();
    }

}
