package com.rh.note.exception;

/**
 * 未知逻辑错误
 */
public class UnknownLogicException extends ApplicationException {
    public UnknownLogicException() {
        super(ViewErrorCodeEnum.UNKNOWN_LOGIC_EXCEPTION);
    }
}
