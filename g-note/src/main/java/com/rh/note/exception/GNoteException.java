package com.rh.note.exception;

public class GNoteException extends RuntimeException {

    private Integer code;
    private Object data;

    public GNoteException(GErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public GNoteException(GErrorCodeEnum errorCode, Object data) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.data = data;
    }

}
