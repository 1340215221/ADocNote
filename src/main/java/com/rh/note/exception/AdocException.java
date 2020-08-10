package com.rh.note.exception;

import lombok.Data;

@Data
public class AdocException extends RuntimeException {
    private Object result;

    public AdocException(String errorMessage) {
        super(errorMessage);
    }

    public AdocException(String errorMessage, Throwable e) {
        super(errorMessage);
        e.initCause(this);
    }
}
