package com.rh.note.common;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 随容器创建, 但不参与控件组装
 */
public interface ISingletonDynamicBuilder {
    /**
     * 初始化
     */
    @PostConstruct
    void init();
    /**
     * 销毁方法
     */
    @PreDestroy
    void destroy();
}
