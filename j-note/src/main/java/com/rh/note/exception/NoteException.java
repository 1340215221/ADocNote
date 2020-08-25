package com.rh.note.exception;

import lombok.Data;

@Data
public class NoteException extends RuntimeException {
    private Integer code;
    private Object result;

    public NoteException(String errorMessage) {
        super(errorMessage);
    }

    public NoteException(String errorMessage, Throwable e) {
        super(errorMessage);
        e.initCause(this);
    }

    public NoteException(ErrorCodeEnum error) {
        super(error.getMsg());
        this.code = error.getCode();
    }

    public NoteException(ErrorCodeEnum error, Throwable e) {
        super(error.getMsg(), e);
        this.code = error.getCode();
    }
}
