package com.rh.note.aspect;

import com.rh.note.common.ActionResult;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.NoteException;
import com.rh.note.exception.RequestParamsValidException;
import com.rh.note.util.aop.IAdocMethodInterceptor;
import com.rh.note.util.aop.MethodInterceptorParam;
import com.rh.note.view.ExceptionDialogRunView;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * action全局异常处理拦截器
 */
@Slf4j
public class GlobalExceptionInterceptor implements IAdocMethodInterceptor<GlobalExceptionHandler> {
    @Override
    public Object apply(@NonNull MethodInterceptorParam<GlobalExceptionHandler> param) throws Throwable {
        try {
            return param.getResult();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * 异常处理
     */
    private void handleException(Exception e) {
        if (e == null) {
            log.error("系统未知异常 ╮(╯_╰)╭");
            ExceptionDialogRunView.create(ExceptionDialogRunView.operation_failed);
            return;
        }
        if (e instanceof RequestParamsValidException || e instanceof IllegalArgumentException) {
            log.error("关键业务参数错误, ", e);
            ExceptionDialogRunView.create(ExceptionDialogRunView.found_system_vulnerabilities);
            return;
        }
        if (e instanceof NoteException) {
            log.error("发生业务异常, ", e);
            ExceptionDialogRunView.create(e.getMessage());
            return;
        }
        log.error("未处理错误异常", e);
        ExceptionDialogRunView.create(ExceptionDialogRunView.operation_failed);
    }
}
