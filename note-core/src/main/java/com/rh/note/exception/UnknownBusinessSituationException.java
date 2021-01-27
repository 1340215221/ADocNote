package com.rh.note.exception;

/**
 * 未知业务情况异常
 */
public class UnknownBusinessSituationException extends ApplicationException {
    public UnknownBusinessSituationException() {
        super(ErrorCodeEnum.UNKNOWN_BUSINESS_SITUATION_ERROR);
    }
}
