package com.rh.note.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * swing异步执行 注解
 */
@Indexed
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwingAsync {
}
