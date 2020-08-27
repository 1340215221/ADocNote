package com.rh.note.exception;

public class GNoteException extends RuntimeException {

    private Integer code;
    private Object data;

    public GNoteException(GErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

}
