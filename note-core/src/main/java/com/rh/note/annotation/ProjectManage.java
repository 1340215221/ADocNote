package com.rh.note.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目管理 组件
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProjectManage {
    /**
     * 组件id
     * 如果存在变量, 使用{}占位, 仅限一个变量
     */
    String value() default "";
}
