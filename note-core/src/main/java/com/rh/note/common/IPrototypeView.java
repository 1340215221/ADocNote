package com.rh.note.common;

import com.rh.note.api.WorkLoaderApi;
import com.rh.note.util.SpringUtil;
import com.rh.note.util.StrUtils;
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
public abstract class IPrototypeView<B extends IPrototypeBuilder, C> {

    /**
     * 对应的builder
     */
    private B builder;

    /**
     * 创建
     * 不是所有的view都需要create方法
     */
    protected <R extends IPrototypeView<B, C>> @NotNull R create(Object arg) {
        WorkLoaderApi workContextApi = SpringUtil.get(WorkLoaderApi.class);
        builder = workContextApi.createWorkPrototype(getBuilderName(), arg);
        return (R) this;
    }

    /**
     * 初始化
     */
    protected <R extends IPrototypeView<B, C>> @Nullable R init(String args) {
        // 获得spring容器
        ApplicationContext app = ViewContextUtil.context.get();
        if (app == null) {
            return null;
        }
        // 生成builder实例名
        String builderInstanceName = getBuilderInstanceName(args);
        if (StringUtils.isBlank(builderInstanceName)) {
            return null;
        }
        // 获得builder
        try {
            Object bean = app.getBean(builderInstanceName);
            builder = (B) bean;
            return (R) this;
        } catch (Exception e) {
            log.error("[init] error, {}", e.getMessage());
        }
        return null;
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
     * 获得控件
     */
    protected @NotNull C getComponent(@NonNull Function<B, C> function) {
        return function.apply(builder);
    }

    /**
     * 获取spring对象名
     * 多例实例名字
     */
    private @Nullable String getBuilderInstanceName(String param) {
        Class<B> builderClass = getBuilderType();
        if (builderClass == null) {
            return null;
        }
        try {
            Field field = builderClass.getDeclaredField("builder_name");
            field.setAccessible(true);
            String builderName = (String) field.get(builderClass);
            if (StringUtils.isBlank(param)) {
                return builderName;
            }
            return StrUtils.replacePlaceholder(builderName, param);
        } catch (Exception e) {
            log.error("[getBuilderInstanceName] error", e);
            return null;
        }
    }

    /**
     * 获取spring对象名
     * 多例模板名字
     */
    private @Nullable String getBuilderName() {
        Class<B> builderClass = getBuilderType();
        if (builderClass == null) {
            return null;
        }
        try {
            Field field = builderClass.getDeclaredField("builder_name");
            field.setAccessible(true);
            return (String) field.get(builderClass);
        } catch (Exception e) {
            log.error("[getBuilderInstanceName] error", e);
            return null;
        }
    }

}
