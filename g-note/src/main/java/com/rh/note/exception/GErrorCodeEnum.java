package com.rh.note.exception;

public enum GErrorCodeEnum {
    PARAMETER_ERROR(1000, "参数错误"),
    ;
    private Integer code;
    private String msg;
    GErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

}