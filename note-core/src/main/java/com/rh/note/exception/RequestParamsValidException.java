package com.rh.note.exception;

/**
 * 开发过程中应该解决的错误
 */
public class RequestParamsValidException extends ApplicationException {

    public RequestParamsValidException() {
        super(ErrorCodeEnum.PARAMETER_ERROR);
    }

}
