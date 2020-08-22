package com.rh.note.exception;

public class RequestParamsValidException extends NoteException{

    public RequestParamsValidException() {
        super(ErrorCodeEnum.PARAMETER_ERROR);
    }
}
