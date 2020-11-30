package com.rh.note.exception;

public class RequestParamsValidException extends ApplicationException {

    public RequestParamsValidException() {
        super(ViewErrorCodeEnum.PARAMETER_ERROR);
    }

}
