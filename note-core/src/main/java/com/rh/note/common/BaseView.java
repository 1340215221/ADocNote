package com.rh.note.common;

import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;

/**
 * 基础视图
 * B 视图构建者
 * C swing控件
 */
@Slf4j
public abstract class BaseView<B extends BaseBuilder, C> {

    private final ApplicationContext context = ViewThreadContext.getThreadContext();
    private BaseBuilder builder;

    /**
     * 初始化builder
     */
    protected <R extends BaseView> @Nullable R init() {
        Class<B> clazz = getBuilderType();
        return (R) this;
    }

    /**
     * 获得泛型B的实际类型
     */
    private @NotNull Class<B> getBuilderType() {
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            return (Class<B>) type.getActualTypeArguments()[0];
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_BUILDER_THROUGH_GENERICS);
        }
    }
}
