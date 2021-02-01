package com.rh.note.exception;

/**
 * 创建adoc文件名重复 异常
 */
public class CreateDuplicateAdocFileNameException extends ApplicationException {
    public CreateDuplicateAdocFileNameException() {
        super(ErrorCodeEnum.FAILED_TO_CREATE_ADOC_FILE);
    }
}
