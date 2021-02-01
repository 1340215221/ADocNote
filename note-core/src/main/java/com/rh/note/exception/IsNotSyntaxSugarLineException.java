package com.rh.note.exception;

/**
 * 不是快捷语法行 异常
 */
public class IsNotSyntaxSugarLineException extends ApplicationException {

    public IsNotSyntaxSugarLineException() {
        super(ErrorCodeEnum.IS_NOT_SYNTAX_SUGAR_LINE);
    }

}
