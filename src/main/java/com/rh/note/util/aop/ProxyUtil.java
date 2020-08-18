package com.rh.note.util.aop;

import com.rh.note.config.AOPConfigEnum;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.NoteException;
import com.rh.note.util.LambdaUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
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
    public static <T extends Annotation> boolean matchAnnotationOnClass(Class targetClass, Class<T> annotationClass) {
        if (targetClass == null || annotationClass == null) {
            return false;
        }
        return targetClass.getAnnotation(annotationClass) != null;
    }

    /**
     * 判断类的方法上是否有该注解
     */
    public static <T extends Annotation> boolean matchAnnotationOnMethod(Class targetClass, Class<T> annotationClass) {
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
     * 初始化
     */

    /**
     * 生成代理对象
     */
    public <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        MethodInterceptor[] handlers = this.getHandler(clazz);
        if (ArrayUtils.isEmpty(handlers)) {
            return LambdaUtil.hiddenExceptionSup(clazz::newInstance);
        }
        enhancer.setCallbacks(handlers);
        Object bean = enhancer.create();
        if (bean == null || !clazz.isInstance(bean)) {
            throw new NoteException(ErrorMessage.DYNAMIC_PROXY_CREATION_FAILED);
        }
        return (T) bean;
    }

    /**
     * 获取方法拦截器, 通过class
     */
    private <T> MethodInterceptor[] getHandler(Class<T> clazz) {
        return Arrays.stream(AOPConfigEnum.values())
                .filter(e -> e.match(clazz))
                .map(AOPConfigEnum::getInterceptor)
                .toArray(MethodInterceptor[]::new);
    }

}
