package com.rh.note.common;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础视图
 * B 视图构建者
 * C swing控件
 */
@Slf4j
public abstract class BaseView<B extends BaseBuilder, C> {

    private final ConfigurableApplicationContext context = ViewThreadContext.getThreadContext();
    private B builder;

    /**
     * 从容器中获得builder
     */
    protected <R extends BaseView> @Nullable R init(String... args) {
        // 通过泛型获得对应的 builder类型
        Class<B> clazz = getBuilderType();
        // 获得 builder类名
        String className = getBuilderClassName(clazz, args);
        if (StringUtils.isBlank(className)) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_BUILDER_CLASS_NAME);
        }
        // 从容器中获得builder
        try {
            builder = (B) context.getBean(className);
            return (R) this;
        } catch (Exception e) { // todo 应该只捕获对象不存在异常
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
     * 获得 builder类名
     */
    private @Nullable String getBuilderClassName(Class<B> clazz, String[] args) {
        if (clazz == null) {
            return null;
        }
        ComponentBean annotation = AnnotationUtils.findAnnotation(clazz, ComponentBean.class);
        if (annotation == null) {
            return null;
        }
        Map<String, Object> attributeMap = AnnotationUtils.getAnnotationAttributes(annotation);
        if (MapUtils.isEmpty(attributeMap)) {
            return null;
        }
        Object beanName = attributeMap.get("name");
        // 类名作为对象名
        if (!(beanName instanceof String) || StringUtils.isBlank((String) beanName)) {
            return clazz.getSimpleName();
        }
        // 替换对象名参数
        return replaceBeanNameParam(((String) beanName), args);
    }

    /**
     * 替换对象名中的参数
     */
    private @Nullable String replaceBeanNameParam(String beanName, String[] args) {
        if (StringUtils.isBlank(beanName)) {
            return null;
        }
        if (!beanName.contains("{}")) {
            return beanName;
        }
        if (ArrayUtils.isEmpty(args)) {
            return beanName.replaceAll("\\{\\}", "");
        }
        Matcher matcher = Pattern.compile("([^\\s{}]|\\{\\})").matcher(beanName);
        StringBuilder newBeanName = new StringBuilder();
        int paramIndex = 0;
        while (matcher.find()) {
            String str = matcher.group(1);
            if (!"{}".equals(str)) {
                newBeanName.append(str);
                continue;
            }
            if (ArrayUtils.isEmpty(args) || args.length <= paramIndex) {
                continue;
            }
            String arg = args[paramIndex];
            paramIndex++;
            if (StringUtils.isBlank(arg)) {
                continue;
            }
            newBeanName.append(arg);
        }
        return newBeanName.toString();
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
