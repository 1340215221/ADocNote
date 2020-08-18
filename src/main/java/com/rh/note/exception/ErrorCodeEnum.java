package com.rh.note.exception;

import lombok.Getter;

/**
 * 异常枚举
 */
@Getter
public enum ErrorCodeEnum {
    PARAMETER_ERROR(1000, "参数错误"),
    FILE_READ_FAILED(1001, "文件读取失败"),
    DYNAMIC_PROXY_FAILED(1002, "动态代理失败"),
    ;
    private Integer code;
    private String msg;
    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
