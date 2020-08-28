package com.rh.note.exception;

/**
 * 结果异常
 */
public class ResultException extends GNoteException {

    public ResultException(GErrorCodeEnum errorCode) {
        super(errorCode);
    }

}
