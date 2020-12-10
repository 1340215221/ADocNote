package com.rh.note.common;

import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 多例builder模板
 */
public interface IPrototypeBuilder {
    /**
     * 初始化swing控件
     */
    @PostConstruct
    void init();
    /**
     * builder实例对象添加到spring容器时设置的beanName
     */
    @NotNull String getInstanceName();
    /**
     * 销毁方法
     */
    @PreDestroy
    void destroy();
}