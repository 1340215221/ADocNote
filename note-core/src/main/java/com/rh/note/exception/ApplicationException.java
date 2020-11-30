package com.rh.note.exception;

public class ApplicationException extends RuntimeException {

    private Integer code;
    private String msg;
    private Object data;

    public ApplicationException(IErrorCode errorCode) {
        super(errorCode.getMsg());
        code = errorCode.getCode();
    }

    public ApplicationException(IErrorCode errorCode, Throwable e) {
        super(errorCode.getMsg());
        code = errorCode.getCode();
        this.initCause(e);
    }

}
