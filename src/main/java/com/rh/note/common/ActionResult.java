package com.rh.note.common;

import com.sun.jnlp.FileOpenServiceNSBImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * action返回值
 */
@Data
public class ActionResult<T> {
    /**
     * 成功code
     */
    private static final Integer success_code = 200;
    /**
     * 成功msg
     */
    private static final String success_msg = "success";
    /**
     * 未知错误code
     */
    private static final Integer error_code = -1;
    /**
     * 未处理异常code
     */
    private static final Integer no_handle_code = 500;
    /**
     * 系统错误异常msg
     */
    private static final String error_msg = "系统繁忙, 请等待";
    /**
     * 返回结果
     */
    private T data;
    /**
     * 错误提示信息
     */
    private String msg;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 成功
     */
    public static <T> ActionResult<T> ok() {
        return new ActionResult<T>()
                .setCode(success_code)
                .setMsg(success_msg);
    }

    /**
     * 成功
     */
    public static <T> ActionResult<T> ok(T data) {
        return new ActionResult<T>()
                .setCode(success_code)
                .setMsg(success_msg)
                .setData(data);
    }

    /**
     * 失败
     */
    public static <T> ActionResult<T> error(Integer code, String msg) {
        if (code == null || StringUtils.isBlank(msg)) {
            return error();
        }
        return new ActionResult<T>()
                .setCode(code)
                .setMsg(msg);
    }

    /**
     * 未知错误
     */
    public static <T> ActionResult<T> error() {
        return new ActionResult<T>()
                .setCode(error_code)
                .setMsg(error_msg);
    }

    /**
     * 错误异常
     */
    public static <T> ActionResult<T> errorForNoHandle() {
        return new ActionResult<T>()
                .setCode(no_handle_code)
                .setMsg(error_msg);
    }
}
