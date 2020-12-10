package com.rh.note.common;

import javax.annotation.PostConstruct;

/**
 * 随容器创建, 但不参与控件组装
 */
public interface ISingletonDynamicBuilder extends ISingletonBuilder {
    /**
     * 初始化
     */
    @PostConstruct
    void init();
}
