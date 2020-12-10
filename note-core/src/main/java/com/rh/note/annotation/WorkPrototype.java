package com.rh.note.annotation;

/**
 * 工作窗口 多例注解
 */
public @interface WorkPrototype {
    /**
     * 多例模板的beanName
     */
    String value() default "";
}