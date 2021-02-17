package com.rh.note.common;

import cn.hutool.core.util.ArrayUtil;
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
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 基础视图
 * B 视图构建者
 * C swing控件
 */
@Slf4j
public abstract class BaseView<B extends BaseBuilder, C> {

    /**
     * 当前线程所属容器
     */
    @NotNull
    private final ConfigurableApplicationContext context = ViewThreadContext.getThreadContext();
    /**
     * 对应的构造者
     */
    @NotNull
    private B builder;
    /**
     * builder在spring容器中的名字
     */
    @NotNull
    private String beanName;

    /**
     * 创建对象
     */
    protected <R extends BaseView> @NotNull R create(IArgsConstructorBean arg) {
        Class<B> clazz = getBuilderType();
        String beanNameNoArgs = getBuilderBeanName(clazz);
        if (StringUtils.isBlank(beanNameNoArgs)) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_BUILDER_CLASS_NAME);
        }
        builder = (B) context.getBean(beanNameNoArgs, arg);
        String beanNameWithArgs = getBuilderBeanName(clazz, arg != null ? arg.getBeanNameArgs() : null);
        if (StringUtils.isBlank(beanNameWithArgs)) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_BUILDER_CLASS_NAME);
        }
        context.getBeanFactory().registerSingleton(beanNameWithArgs, builder);
        beanName = beanNameWithArgs;
        return (R) this;
    }

    /**
     * 销毁对象<br/>
     * 只有多例的builder才能执行该方法
     */
    protected void destroy() {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        try {
            beanFactory.destroyBean(builder);
            if (!(beanFactory instanceof DefaultListableBeanFactory)) {
                throw new ApplicationException(ErrorCodeEnum.FAILED_TO_DESTROY_CONTROL);
            }
            ((DefaultListableBeanFactory) beanFactory).destroySingleton(beanName);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_DESTROY_CONTROL, e);
        }
    }

    /**
     * 从容器中获得builder
     */
    protected <R extends BaseView> @Nullable R init(String... args) {
        // 通过泛型获得对应的 builder类型
        Class<B> clazz = getBuilderType();
        // 获得 builder类名
        String beanName = getBuilderBeanName(clazz, args);
        if (StringUtils.isBlank(beanName)) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_BUILDER_CLASS_NAME);
        }
        // 从容器中获得builder
        if (!context.containsBean(beanName)) {
            List<String> beanNames = getBeanNameByBuilders(clazz, args);
            beanName = beanNames.stream()
                    .filter(context::containsBean)
                    .findFirst()
                    .orElse(null);
        }
        if (beanName == null) {
            return null;
        }
        builder = (B) context.getBean(beanName);
        this.beanName = beanName;
        return (R) this;
    }

    /**
     * 获得控件
     */
    protected @NotNull C getComponent(@NonNull Function<B, C> function) {
        return function.apply(builder);
    }

    /**
     * 获得builders中的对象名
     */
    private @NotNull List<String> getBeanNameByBuilders(Class<B> clazz, String[] args) {
        if (clazz == null) {
            return Collections.emptyList();
        }
        ComponentBean annotation = AnnotationUtils.findAnnotation(clazz, ComponentBean.class);
        if (annotation == null) {
            return Collections.emptyList();
        }
        Map<String, Object> attributeMap = AnnotationUtils.getAnnotationAttributes(annotation);
        if (MapUtils.isEmpty(attributeMap)) {
            return Collections.emptyList();
        }
        // 从builders中获得对象名
        Object builders = attributeMap.get("builders");
        if (ArrayUtil.isEmpty(builders)) {
            return Collections.emptyList();
        }
        return Arrays.stream(((Object[]) builders))
                .filter(e -> e instanceof Class)
                .map(bClass -> AnnotationUtils.findAnnotation((Class) bClass, ComponentBean.class))
                .filter(Objects::nonNull)
                .map(AnnotationUtils::getAnnotationAttributes)
                .filter(MapUtils::isNotEmpty)
                .map(map -> (String) map.get("name"))
                .filter(StringUtils::isNotBlank)
                .map(e -> replaceBeanNameParam(e, args))
                .collect(Collectors.toList());
    }

    /**
     * 获得 builder类名
     */
    private @Nullable String getBuilderBeanName(Class<B> clazz, String[] args) {
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
        // 从name中获得对象名
        Object beanName = attributeMap.get("name");
        if (StringUtils.isBlank((CharSequence) beanName)) {
            // 类名作为对象名
            return clazz.getSimpleName();
        }
        // 替换对象名参数
        return replaceBeanNameParam(((String) beanName), args);
    }

    /**
     * 获得 builder对象名
     */
    private @Nullable String getBuilderBeanName(Class<B> clazz) {
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
        if (beanName instanceof String && StringUtils.isNotBlank((CharSequence) beanName)) {
            return (String) beanName;
        }
        // 类名作为对象名
        return clazz.getSimpleName();
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
