package com.rh.note.exception;

/**
 * 图形异常码
 */
public enum ViewErrorCodeEnum implements IErrorCode {
    PARAMETER_ERROR(1000, "参数错误"),
    UNKNOWN_LOGIC_EXCEPTION(1001, "未知逻辑错误"),
    ;
    private Integer code;
    private String msg;
    ViewErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
