package com.rh.note.api;

import com.rh.note.annotation.WorkPrototype;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
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
     * 初始化工作窗口多例对象
     */
    public <T> void initWorkPrototype(Class<T> clazz, String... args) {
        if (clazz == null) {
            return;
        }
        AnnotationConfigApplicationContext workContext = new AnnotationConfigApplicationContext();
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(clazz);
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        if (!metadata.hasAnnotation(WorkPrototype.class.getName())) {
            return;
        }
        Map<String, Object> attributeMap = metadata.getAnnotationAttributes(WorkPrototype.class.getName());
        if (MapUtils.isEmpty(attributeMap)) {
            return;
        }
        Object value = attributeMap.get("value");
        if (!(value instanceof String) || StringUtils.isBlank(((String) value))) {
            return;
        }
        String beanName = replaceParam(((String) value), args);
        workContext.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 加载工作窗口容器
     */
    public void startWorkContext() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(WorkSingleton.class));
        Set<BeanDefinition> definitions = scanner.findCandidateComponents("com.rh.note.builder.work");
        AnnotationConfigApplicationContext workContext = new AnnotationConfigApplicationContext();
        definitions.forEach(d -> {
            Map<String, Object> map = ((ScannedGenericBeanDefinition) d).getMetadata().getAnnotationAttributes(WorkSingleton.class.getName());
            Object value = map.get("value");
            if (value instanceof String && StringUtils.isNotBlank(((String) value))) {
                workContext.registerBeanDefinition(d.getBeanClassName(), d);
            }
        });
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
        String beanName = replaceParam(componentId, args);
        if (StringUtils.isBlank(beanName)) {
            return;
        }
        app.removeBeanDefinition(beanName);
    }

    /**
     * 占位符
     */
    private static final String placeholder_regex = "\\{\\}";
    private static final String placeholder = "{}";

    /**
     * 字符串参数替换
     */
    private @Nullable String replaceParam(String str, String... args) {
        if (StringUtils.isBlank(str) || ArrayUtils.isEmpty(args)) {
            return str;
        }
        String[] strArr = str.split(placeholder_regex);
        if (ArrayUtils.isEmpty(strArr)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (String arg : args) {
            int index = sb.indexOf(placeholder);
            if (index < 0) {
                throw new ApplicationException(ErrorCodeEnum.THE_PLACEHOLDER_AND_THE_NUMBER_OF_PARAMETERS_ARE_NOT_EQUAL);
            }
            sb.replace(index, index + 2, arg);
        }
        return sb.toString();
    }

}
