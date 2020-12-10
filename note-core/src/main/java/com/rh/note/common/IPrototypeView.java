package com.rh.note.common;

import com.rh.note.api.WorkContextApi;
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
    protected <R extends IPrototypeView<B, C>> @NotNull R create(Object args) {
        WorkContextApi workContextApi = SpringUtil.get(WorkContextApi.class);
        builder = workContextApi.createWorkPrototype(getBuilderType(), args);
        return (R) this;
    }

    /**
     * 初始化
     */
    public <R extends IPrototypeView<B, C>> @Nullable R init(String args) {
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
        } catch (Exception e) {
            log.error("[init] error", e);
        }
        return (R) this;
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
        if (StringUtils.isBlank(param)) {
            return null;
        }
        Class<B> builderClass = getBuilderType();
        if (builderClass == null) {
            return null;
        }
        try {
            Field field = builderClass.getDeclaredField("builder_name");
            String builderName = (String) field.get(builderClass);
            return StrUtils.replacePlaceholder(builderName, param);
        } catch (Exception e) {
            log.error("[getBuilderInstanceName] error", e);
            return null;
        }
    }

}