package com.rh.note.exception;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 异常的错误信息
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnum {
    // 基础功能
    FAILED_TO_GET_BUILDER_THROUGH_GENERICS(0000, "通过泛型获取构建者失败"),
    FAILED_TO_GET_THE_BUILDER_CLASS_NAME(0001, "获取builder类名失败"),
    PARAMETER_ERROR(0002, "参数错误"),
    UNKNOWN_BUSINESS_SITUATION_ERROR(0003, "未知业务情况错误"),
    // 语法
    THE_READ_ME_FILE_DOES_NOT_HAVE_A_FIRST_LEVEL_TITLE(1000, "该ReadMe文件没有一级标题"),
    ;
    @NonNull
    private Integer code;
    @NonNull
    private String msg;
}
