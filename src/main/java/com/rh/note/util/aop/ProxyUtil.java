package com.rh.note.util.aop;

import com.rh.note.config.AOPConfigEnum;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.NoteException;
import com.rh.note.util.LambdaUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static boolean match(Class<Annotation> annotationClass, Class clazz) {
        return matchAnnotationOnClass(clazz, annotationClass)
                || matchAnnotationOnMethod(clazz, annotationClass);
    }


    /**
     * 生成代理对象
     */
    public <T> Object getBean(Class<T> clazz) {
        if (clazz == null) {
            throw new NoteException(ErrorCodeEnum.DYNAMIC_PROXY_FAILED);
        }
        T target = LambdaUtil.hiddenExceptionSup(clazz::newInstance);
        Map<Class<Annotation>, INoteMethodInterceptor> map = Arrays.stream(AOPConfigEnum.values())
                .filter(e -> match(e.getAnnotationClass(), clazz))
                .collect(Collectors.toMap(AOPConfigEnum::getAnnotationClass, AOPConfigEnum::getInterceptor));
        if (MapUtils.isEmpty(map)) {
            return target;
        }
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(),
                new NoteMethodInterceptor(target, map));
    }

}
