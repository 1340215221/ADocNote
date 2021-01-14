package com.rh.note.common;

import org.springframework.context.ApplicationContext;

/**
 * 视图
 */
public interface BaseView {
    ThreadLocal<ApplicationContext> viewContext = new ThreadLocal<>();

    /**
     * 清空当前线程的容器
     */
    static void clearThreadContext() {
        viewContext.set(null);
    }

    /**
     * 设置当前线程的容器
     */
    static void setThreadContext(ApplicationContext context) {
        if (context == null) {
            return;
        }
        viewContext.set(context);
    }
}
