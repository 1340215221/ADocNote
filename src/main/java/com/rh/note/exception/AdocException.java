package com.rh.note.exception;

import com.rh.note.constant.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdocException extends RuntimeException {

    private Object result;

    public AdocException(ErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
    }

    public AdocException(ErrorCodeEnum errorCode, Throwable e) {
        super(errorCode.getMsg());
        e.initCause(this);
    }

}
