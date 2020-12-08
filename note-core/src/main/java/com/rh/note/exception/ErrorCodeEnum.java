package com.rh.note.exception;

import com.rh.note.common.IErrorCode;

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
    INCLUDE_TARGET_TO_THE_FILE_CANNOT_BE_OPENED(3007, "include指向文件无法打开"),
    GIT_COMMIT_ERROR(3008, "git commit 失败"),
    GIT_PULL_ERROR(3009, "git pull 失败"),
    THE_PLACEHOLDER_AND_THE_NUMBER_OF_PARAMETERS_ARE_NOT_EQUAL(4000, "占位符{}和参数数量不等"),
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
