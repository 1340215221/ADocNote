package com.rh.note.exception;

import lombok.Data;

/**
 * 异常
 */
@Data
public class ApplicationException extends RuntimeException {
    protected Integer code;
    protected Object data;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        code = errorCode.getCode();
    }

    public ApplicationException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getMsg());
        code = errorCode.getCode();
        this.initCause(e);
    }
}
