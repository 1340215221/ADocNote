package com.rh.note.common;

import javax.annotation.PreDestroy;

/**
 * 构建窗口控件
 */
public interface IFrameBuilder extends ISingletonBuilder {
    /**
     * 销毁方法
     */
    @PreDestroy
    void destroy();
}
