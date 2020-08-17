package com.rh.note.aspect;

import com.rh.note.util.ProceedSupplier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 切面参数
 */
@Getter
@Setter
public class DoActionLogAspectParam {

    /**
     * 注解
     */
    private DoActionLog annotation;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法参数
     */
    private Object[] args;
    /**
     * 返回值
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Object result;
    /**
     * 获取返回值方法
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private ProceedSupplier supplier;

    public DoActionLogAspectParam(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        annotation = method.getAnnotation(DoActionLog.class);
        methodName = signature.getName();
        args = joinPoint.getArgs();
        supplier = joinPoint::proceed;
    }

    /**
     * 执行切点方法, 并返回方法结果
     */
    public Object getResult() throws Throwable {
        if (supplier != null) {
            result = supplier.get();
            supplier = null;
        }
        return result;
    }

}
