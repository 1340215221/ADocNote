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
    /**
     * 文件读取失败
     */
    FILE_READ_FAILED(1001, "文件读取失败"),
    /**
     * 获取当前行内容失败
     */
    FAILED_TO_GET_CURRENT_LINE_CONTENT(1002, "获取当前行内容失败"),
    ;
    private Integer code;
    private String msg;
    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
