package com.rh.note.exception;

/**
 * 异常的错误信息
 */
public enum ErrorCodeEnum implements IErrorCode {
    CANNOT_OPEN_A_PROJECT_WITHOUT_A_TITLE(2000, "无法打开一个没有标题的项目"),
    READ_FILE_ERROR(3000, "读取文件失败"),
    ;
    private Integer code;
    private String msg;
    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
