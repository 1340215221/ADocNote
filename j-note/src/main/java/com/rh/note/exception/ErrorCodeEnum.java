package com.rh.note.exception;

import lombok.Getter;

/**
 * 异常枚举
 */
@Getter
public enum ErrorCodeEnum {
    PARAMETER_ERROR(1000, "参数错误"),
    FILE_READ_FAILED(1001, "文件读取失败"),
    FAILED_TO_GET_CURRENT_LINE_CONTENT(1002, "获取当前行内容失败"),
    FILE_WRITE_FAILED(1003, "文件写入失败"),
    FILE_ALREADY_EXIST(1004, "文件已存在"),
    ;
    private Integer code;
    private String msg;
    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
