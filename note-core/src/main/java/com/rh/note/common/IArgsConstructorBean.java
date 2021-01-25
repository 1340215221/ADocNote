package com.rh.note.common;

import org.jetbrains.annotations.NotNull;

/**
 * 构造方法带参数的spring对象
 */
public interface IArgsConstructorBean {
    /**
     * 获得spring对象名参数数组
     */
    @NotNull String[] getBeanNameArgs();
}
