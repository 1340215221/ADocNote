package com.rh.note.common;

import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;

/**
 * 视图线程容器
 */
public interface ViewThreadContext {
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

    /**
     * 获取当前线程的容器
     */
    static @Nullable ApplicationContext getThreadContext() {
        return viewContext.get();
    }
}
