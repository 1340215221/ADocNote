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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
