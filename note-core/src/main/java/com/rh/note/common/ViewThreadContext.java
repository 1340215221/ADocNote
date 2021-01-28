package com.rh.note.common;

import com.rh.note.bean.ThreadContextBean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 视图线程容器
 */
public interface ViewThreadContext {
    ThreadLocal<ThreadContextBean> viewContext = new ThreadLocal<>();

    /**
     * 清空当前线程的容器
     */
    static void clearThreadContext() {
        viewContext.set(null);
    }

    /**
     * 设置当前线程的容器
     */
    static void setThreadContextBean(ThreadContextBean bean) {
        if (bean == null) {
            return;
        }
        viewContext.set(bean);
    }

    /**
     * 获取当前线程的容器
     */
    static @Nullable ThreadContextBean getThreadContextBean() {
        return viewContext.get();
    }

    /**
     * 获取当前线程的容器
     */
    static @NotNull ConfigurableApplicationContext getThreadContext() {
        return viewContext.get().getContext();
    }

    /**
     * 获取当前线程的容器
     */
    static @Nullable ConfigurableApplicationContext getThreadContextOrNull() {
        ThreadContextBean bean = viewContext.get();
        return bean != null ? bean.getContext() : null;
    }
}
