package com.rh.note.util.aop;

import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.NoteException;
import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 静态代理工具
 */
public class ProxyUtil {

    /**
     * 判断类上是否有该注解
     */
    private static <T extends Annotation> boolean matchAnnotationOnClass(Class targetClass, Class<T> annotationClass) {
        if (targetClass == null || annotationClass == null) {
            return false;
        }
        return targetClass.getAnnotation(annotationClass) != null;
    }

    /**
     * 判断类的方法上是否有该注解
     */
    private static <T extends Annotation> boolean matchAnnotationOnMethod(Class targetClass, Class<T> annotationClass) {
        if (targetClass == null || annotationClass == null) {
            return false;
        }
        Method[] methods = targetClass.getDeclaredMethods();
        if (ArrayUtils.isEmpty(methods)) {
            return false;
        }
        return Arrays.stream(methods)
                .anyMatch(method -> method.getAnnotation(annotationClass) != null);
    }

    /**
     * 通过目标类或方法上的注解匹配拦截器
     */
    public static boolean match(Class<Annotation> annotationClass, Method method) {
        return matchAnnotationOnClass(method.getDeclaringClass(), annotationClass)
                || matchAnnotationOnMethod(method.getDeclaringClass(), annotationClass);
    }


    /**
     * 生成代理对象
     */
    public <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            throw new NoteException(ErrorMessage.DYNAMIC_PROXY_CREATION_FAILED);
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new NoteMethodInterceptor());
        Object bean = enhancer.create();
        if (bean == null || !clazz.isInstance(bean)) {
            throw new NoteException(ErrorMessage.DYNAMIC_PROXY_CREATION_FAILED);
        }
        return (T) bean;
    }

}
