package com.rh.note.annotation;

/**
 * 工作窗口 多例注解
 */
public @interface WorkPrototype {
    String value() default "";
}