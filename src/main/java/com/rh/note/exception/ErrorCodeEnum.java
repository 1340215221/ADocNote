package com.rh.note.exception;

import lombok.Getter;

/**
 * 异常枚举
 */
@Getter
public enum ErrorCodeEnum {
    /**
     * 参数错误
     */
    PARAMETER_ERROR(1000, "参数错误"),
    ;
    private Integer code;
    private String msg;
    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
