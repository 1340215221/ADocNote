package com.rh.note.api;

import com.rh.note.annotation.WorkPrototype;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.common.IPrototypeBuilder;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.util.StrUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
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
public class WorkContextApi {

    /**
     * 父容器
     */
    @Autowired
    private ApplicationContext parentContext;
    /**
     * 工作容器
     */
    private AnnotationConfigApplicationContext app = null;

    /**
     * 获得多例对象
     */

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
    public void startWorkContext() {
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
                workContext.registerBeanDefinition(d.getBeanClassName(), d);
            }
        });
        // 注册多例对象
        ClassPathScanningCandidateComponentProvider scanner2 = new ClassPathScanningCandidateComponentProvider(false);
        scanner2.addIncludeFilter(new AnnotationTypeFilter(WorkPrototype.class));
        Set<BeanDefinition> definitions2 = scanner.findCandidateComponents("com.rh.note");
        definitions2.forEach(d -> {
            d.setScope("prototype");
            Map<String, Object> map = ((ScannedGenericBeanDefinition) d).getMetadata().getAnnotationAttributes(WorkPrototype.class.getName());
            Object value = map.get("value");
            if (value instanceof String && StringUtils.isNotBlank(((String) value))) {
                workContext.registerBeanDefinition((String) value, d);
            } else {
                workContext.registerBeanDefinition(d.getBeanClassName(), d);
            }
        });
        // 关联父容器
        workContext.setParent(parentContext);
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

}
