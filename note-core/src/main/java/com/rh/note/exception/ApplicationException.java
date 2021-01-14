package com.rh.note.exception;

public class ApplicationException extends RuntimeException {

    private Integer code;
    private String msg;
    private Object data;

    public ApplicationException(ErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        code = errorCode.getCode();
    }

    public ApplicationException(ErrorCodeEnum errorCode, Throwable e) {
        super(errorCode.getMsg());
        code = errorCode.getCode();
        this.initCause(e);
    }

}
