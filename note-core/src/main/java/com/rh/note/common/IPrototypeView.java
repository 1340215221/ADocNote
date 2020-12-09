package com.rh.note.common;

import com.rh.note.api.WorkContextApi;
import com.rh.note.util.SpringUtil;
import com.rh.note.util.ViewContextUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;

public abstract class IPrototypeView<B extends PrototypeBuilder, C> {

    /**
     * 对应的builder
     */
    private B builder;

    /**
     * 创建
     */
    public <R extends IPrototypeView> @NotNull R create(String... args) {
        WorkContextApi workContextApi = SpringUtil.get(WorkContextApi.class);
        builder = workContextApi.createWorkPrototype(getBuilderType(), args);
        return (R) this;
    }

    /**
     * 初始化
     */
    public <R extends IPrototypeView> @Nullable R init(String... args) {
        ApplicationContext app = ViewContextUtil.context.get();
        if (app == null) {
            return null;
        }
        Object bean = app.getBean(getBuilderInstanceName(args));
        builder = (B) bean;
        return (R) this;
    }

    /**
     * 获得泛型B的实际类型
     */
    private Class<B> getBuilderType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<B>) type.getActualTypeArguments()[0];
    }

    /**
     * 获得builder
     */
    protected B getBuilder() {
        return builder;
    }

    /**
     * 获取spring对象名
     * 多例实例名字
     */
    protected abstract @NotNull String getBuilderInstanceName(String... args);

}
