package com.rh.note.common;

import org.jetbrains.annotations.Nullable;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 视图线程容器
 */
public interface ViewThreadContext {
    ThreadLocal<ConfigurableApplicationContext> viewContext = new ThreadLocal<>();

    /**
     * 清空当前线程的容器
     */
    static void clearThreadContext() {
        viewContext.set(null);
    }

    /**
     * 设置当前线程的容器
     */
    static void setThreadContext(ConfigurableApplicationContext context) {
        if (context == null) {
            return;
        }
        viewContext.set(context);
    }

    /**
     * 获取当前线程的容器
     */
    static @Nullable ConfigurableApplicationContext getThreadContext() {
        return viewContext.get();
    }
}
