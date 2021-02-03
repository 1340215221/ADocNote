package com.rh.note.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * swing异步执行 注解<br/>
 * tip 异步执行的方法始终返回null
 */
@Indexed
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwingAsync {
}
