package com.rh.note.annotation;

import com.rh.note.common.BaseBuilder;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.constants.ScopeEnum;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注入注解
 */
@Indexed
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentBean {
    /**
     * 是否为多例
     */
    ScopeEnum scope() default ScopeEnum.SINGLETON;
    /**
     * 对象名
     */
    String name() default "";
    /**
     * 所属窗口
     */
    @AliasFor("value")
    FrameCategoryEnum frame() default FrameCategoryEnum.ABANDONED;
    /**
     * 所属窗口
     */
    @AliasFor("frame")
    FrameCategoryEnum value() default FrameCategoryEnum.ABANDONED;
    /**
     * 多对象名所在builder
     */
    Class<? extends BaseBuilder>[] builders() default {};
}
