package com.rh.note.common;

import org.springframework.context.ApplicationContext;

/**
 * 视图
 */
public interface BaseView {
    ThreadLocal<ApplicationContext> viewContext = new ThreadLocal<>();
}
