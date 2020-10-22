package com.rh.note.exception;

/**
 * 异常的错误信息
 */
public enum ErrorCodeEnum implements IErrorCode {
    CANNOT_OPEN_A_PROJECT_WITHOUT_A_TITLE(2000, "无法打开一个没有标题的项目"),
    READ_FILE_ERROR(3000, "读取文件失败"),
    CANNOT_CREATE_A_FILE_WITH_THE_SAME_NAME(3001, "不能创建同名文件"),
    FILE_CREATION_FAILED(3002, "文件创建失败"),
    FAILED_TO_WRITE_FILE(3003, "写入文件失败"),
    FAILED_TO_DELETE_FILE(3004, "删除文件失败"),
    FILE_RENAMING_FAILED(3005, "文件重命名失败"),
    FAILED_TO_CREATE_AND_OPEN_EDITING_AREA(3006, "编辑区创建并打开失败"),
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
