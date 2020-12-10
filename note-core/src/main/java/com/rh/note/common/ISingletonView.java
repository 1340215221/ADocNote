package com.rh.note.common;

import com.rh.note.util.ViewContextUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

@Slf4j
public abstract class ISingletonView<B extends ISingletonBuilder, C> {

    /**
     * 对应的builder
     */
    private B builder;

    /**
     * 初始化
     */
    protected <R extends ISingletonView<B, C>> @Nullable R init() {
        ApplicationContext app = ViewContextUtil.context.get();
        if (app == null) {
            return null;
        }
        String builderName = getBuilderName();
        if (StringUtils.isBlank(builderName)) {
            return null;
        }
        try {
            Object bean = app.getBean(builderName);
            builder = (B) bean;
            return (R) this;
        } catch (Exception e) {
            log.error("[init] error", e);
            return null;
        }
    }

    /**
     * 获得泛型B的实际类型
     */
    private @Nullable Class<B> getBuilderType() {
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            return (Class<B>) type.getActualTypeArguments()[0];
        } catch (Exception e) {
            log.error("[getBuilderType] error", e);
            return null;
        }
    }

    /**
     * 获取builderName
     */
    private @Nullable String getBuilderName() {
        Class<B> builderClass = getBuilderType();
        if (builderClass == null) {
            return null;
        }
        try {
            Field field = builderClass.getDeclaredField("builder_name");
            return (String) field.get(builderClass);
        } catch (Exception e) {
            log.error("[getBuilderInstanceName] error", e);
            return null;
        }
    }

    /**
     * 获得swing控件
     */
    protected @NotNull C getComponent(@NonNull Function<B, C> function) {
        return function.apply(builder);
    }

}
