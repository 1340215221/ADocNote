package com.rh.note.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * action调用日志
 */
@Aspect
@Slf4j
public class ActionLogAspect {

    /**
     * 切面
     */
    @Around(value = "doAction()")
    public Object addDoActionLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("切面");
        DoActionLogAspectParam param = new DoActionLogAspectParam(joinPoint);
        try {
            String className = "className"; // todo
            log.info("{}.{}, {}, args=[{}]",
                    className,
                    param.getMethodName(),
                    param.getAnnotation().value(),
                    JSONArray.toJSONString(param.getArgs()));
        } catch (Exception e) {
            log.error("ActionLogAspect.addDoActionLog error, 添加action调用日志失败, param=[{}]", JSONObject.toJSONString(param), e);
        }
        return param.getResult();
    }
}
