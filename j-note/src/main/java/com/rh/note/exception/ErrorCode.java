package com.rh.note.exception;

import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {

    // 1000 通用异常
    request_parameter_error(1000, "非法参数异常"),
    // 2000 文件异常
    file_read_failed(2000, "文件读取失败"),
    ;
    private Integer code;
    private String msg;
    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
