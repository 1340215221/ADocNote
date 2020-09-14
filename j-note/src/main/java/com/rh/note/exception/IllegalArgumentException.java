package com.rh.note.exception;

/**
 * 非法参数异常
 */
public class IllegalArgumentException extends ApplicationException {

    public IllegalArgumentException() {
        super(ErrorCode.request_parameter_error);
    }

}
