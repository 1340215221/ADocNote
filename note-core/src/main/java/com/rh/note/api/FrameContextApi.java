package com.rh.note.api;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.ao.LoadContextAO;
import com.rh.note.constants.FrameCategoryEnum;
import groovy.swing.SwingBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 窗口容器操作 接口
 */
@Component
public class FrameContextApi {

    /**
     * 加载一个窗口容器
     */
    public void loadContext(LoadContextAO ao) {
        if (ao == null || ao.getFrameCategoryEnum() == null) {
            return;
        }
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 扫描对象
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ComponentBean.class));
        Set<BeanDefinition> definitions = scanner.findCandidateComponents("com.rh.note");
        if (CollectionUtils.isEmpty(definitions)) {
            return;
        }
        definitions.forEach(d -> {
            Map<String, Object> annotationParamMap = ((ScannedGenericBeanDefinition) d).getMetadata().getAnnotationAttributes(ComponentBean.class.getName());
            if (MapUtils.isEmpty(annotationParamMap)) {
                return;
            }
            // 过滤窗口类型
            Object frameCategory = annotationParamMap.get("frame");
            if (!ao.getFrameCategoryEnum().equals(frameCategory)) {
                return;
            }
            // 获得对象名
            Object name = annotationParamMap.get("name");

            Object value = annotationParamMap.get("value");
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
