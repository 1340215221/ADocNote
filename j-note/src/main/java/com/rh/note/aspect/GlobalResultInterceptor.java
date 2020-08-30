package com.rh.note.aspect;

import com.rh.note.common.ActionResult;
import com.rh.note.common.ActionResultContext;
import com.rh.note.exception.ResultException;
import com.rh.note.util.aop.INoteMethodInterceptor;
import com.rh.note.util.aop.MethodInterceptorParam;
import lombok.NonNull;

/**
 * 全局结果处理
 */
public class GlobalResultInterceptor implements INoteMethodInterceptor {
    @Override
    public Object doResult(@NonNull MethodInterceptorParam param) throws Throwable {
        try {
            Object result = param.getResult();
            new ActionResultContext().setResult(new ActionResult<>().setCode(200).setData(result));
            return result;
        } catch (ResultException e) {
            new ActionResultContext().setResult(new ActionResult<>().setCode(e.getCode()).setMsg(e.getMessage()).setData(e.getData()));
            throw e;
        }
    }
}
