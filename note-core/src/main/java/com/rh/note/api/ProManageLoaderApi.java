package com.rh.note.api;

import com.rh.note.annotation.ProManageSingleton;
import groovy.swing.SwingBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * 项目管理加载器 接口
 */
@Slf4j
@Component
public class ProManageLoaderApi {

    @Autowired
    private ApplicationContext mainContext;
    /**
     * 工作容器
     */
    @Getter
    private AnnotationConfigApplicationContext app = null;

    /**
     * 加载项目管理容器
     */
    public void load() {
        AnnotationConfigApplicationContext workContext = new AnnotationConfigApplicationContext();
        // 注册单例对象
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ProManageSingleton.class));
        Set<BeanDefinition> definitions = scanner.findCandidateComponents("com.rh.note");
        definitions.forEach(d -> {
            Map<String, Object> map = ((ScannedGenericBeanDefinition) d).getMetadata().getAnnotationAttributes(ProManageSingleton.class.getName());
            Object value = map.get("value");
            if (value instanceof String && StringUtils.isNotBlank(((String) value))) {
                workContext.registerBeanDefinition((String) value, d);
            } else {
                String className = d.getBeanClassName();
                String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
                log.warn("" + simpleClassName);
                workContext.registerBeanDefinition(simpleClassName, d);
            }
        });
        // 注册swingBuilder
        AnnotatedGenericBeanDefinition swingBeanDefinition = new AnnotatedGenericBeanDefinition(SwingBuilder.class);
        workContext.registerBeanDefinition("swingBuilder", swingBeanDefinition);
        // 关联父容器
        workContext.setParent(mainContext);
        workContext.refresh();
        app = workContext;
    }
}
