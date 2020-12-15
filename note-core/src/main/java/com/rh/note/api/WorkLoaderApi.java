package com.rh.note.api;

import com.rh.note.annotation.SwingBuilderFactory;
import com.rh.note.annotation.WorkPrototype;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.common.IPrototypeBuilder;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.load.WorkLoader;
import com.rh.note.util.StrUtils;
import groovy.swing.SwingBuilder;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 工作窗口容器 接口
 */
@Component
public class WorkLoaderApi {

    /**
     * 父容器
     */
    @Autowired
    private ApplicationContext mainContext;
    /**
     * 工作容器
     */
    @Getter
    private AnnotationConfigApplicationContext app = null;

    /**
     * 初始化工作窗口多例对象
     */
    public <T extends IPrototypeBuilder> @Nullable T createWorkPrototype(Class<T> clazz, Object... args) {
        if (app == null || clazz == null || ArrayUtils.isEmpty(args)) {
            return null;
        }
        try {
            T builder = app.getBean(clazz, args);
            app.getBeanFactory().registerSingleton(builder.getInstanceName(), builder);
            return builder;
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_CREATE_MULTIPLE_CONTROL, e);
        }
    }

    /**
     * 加载工作窗口容器
     */
    public void loadContext() {
        AnnotationConfigApplicationContext workContext = new AnnotationConfigApplicationContext();
        // 注册单例对象
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(WorkSingleton.class));
        Set<BeanDefinition> definitions = scanner.findCandidateComponents("com.rh.note");
        definitions.forEach(d -> {
            Map<String, Object> map = ((ScannedGenericBeanDefinition) d).getMetadata().getAnnotationAttributes(WorkSingleton.class.getName());
            Object value = map.get("value");
            if (value instanceof String && StringUtils.isNotBlank(((String) value))) {
                workContext.registerBeanDefinition((String) value, d);
            } else {
                String className = d.getBeanClassName();
                String beanName = className.substring(className.lastIndexOf('.') + 1);
                workContext.registerBeanDefinition(beanName, d);
            }
        });
        // 注册多例对象
        ClassPathScanningCandidateComponentProvider scanner2 = new ClassPathScanningCandidateComponentProvider(false);
        scanner2.addIncludeFilter(new AnnotationTypeFilter(WorkPrototype.class));
        Set<BeanDefinition> definitions2 = scanner2.findCandidateComponents("com.rh.note");
        definitions2.forEach(d -> {
            d.setScope("prototype");
            Map<String, Object> map = ((ScannedGenericBeanDefinition) d).getMetadata().getAnnotationAttributes(WorkPrototype.class.getName());
            Object value = map.get("value");
            if (value instanceof String && StringUtils.isNotBlank(((String) value))) {
                workContext.registerBeanDefinition((String) value, d);
            } else {
                String className = d.getBeanClassName();
                String beanName = className.substring(className.lastIndexOf('.') + 1);
                workContext.registerBeanDefinition(beanName, d);
            }
        });
        // 注册swingBuilder工厂
        ClassPathScanningCandidateComponentProvider scanner3 = new ClassPathScanningCandidateComponentProvider(false);
        scanner3.addIncludeFilter(new AnnotationTypeFilter(SwingBuilderFactory.class));
        Set<BeanDefinition> definitions3 = scanner3.findCandidateComponents("com.rh.note");
        definitions3.forEach(d -> {
            Map<String, Object> map = ((ScannedGenericBeanDefinition) d).getMetadata().getAnnotationAttributes(SwingBuilderFactory.class.getName());
            Object value = map.get("value");
            if (value instanceof String && StringUtils.isNotBlank(((String) value))) {
                workContext.registerBeanDefinition((String) value, d);
            } else {
                String className = d.getBeanClassName();
                String beanName = className.substring(className.lastIndexOf('.') + 1);
                workContext.registerBeanDefinition(beanName, d);
            }
        });
        // 注册swingBuilder
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(SwingBuilder.class);
        workContext.registerBeanDefinition("swingBuilder", beanDefinition);
        // 关联父容器
        workContext.setParent(mainContext);
        workContext.refresh();
        app = workContext;
    }

    /**
     * 从容器中删除
     */
    public void deleteBean(String componentId) {
        if (app == null || StringUtils.isBlank(componentId)) {
            return;
        }
        app.removeBeanDefinition(componentId);
    }

    /**
     * 从容器中删除
     */
    public void deleteBean(String componentId, String... args) {
        if (app == null) {
            return;
        }
        String beanName = StrUtils.replacePlaceholder(componentId, args);
        if (StringUtils.isBlank(beanName)) {
            return;
        }
        app.removeBeanDefinition(beanName);
    }

    /**
     * 加载容器
     */
    public void loadComponent() {
        mainContext.getBean(WorkLoader.class, app);
    }
}
