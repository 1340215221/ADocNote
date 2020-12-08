package com.rh.note.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;

public abstract class IView<T> {

    /**
     * 容器
     */
    private static final ThreadLocal<ApplicationContext> context = new ThreadLocal<>();
    /**
     * 对应的builder
     */
    private IBuilder builder;

    /**
     * 初始化
     */
    public <R extends IView> @Nullable R init() {
        ApplicationContext app = context.get();
        if (app == null) {
            return null;
        }
        Object bean = app.getBean(getBuilderName());
        builder = (IBuilder) bean;
        return (R) this;
    }

    /**
     * 获得builder
     */
    protected <R extends IBuilder> R getBuilder() {
        return (R) builder;
    }

    /**
     * 获取spring对象名
     */
    protected abstract @NotNull String getBuilderName();

}
