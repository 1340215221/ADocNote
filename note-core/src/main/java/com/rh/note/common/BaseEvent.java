package com.rh.note.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * 基础事件
 */
public abstract class BaseEvent {
    /**
     * 客户端控件的所属容器
     */
    @Autowired
    private ApplicationContext context;
}
