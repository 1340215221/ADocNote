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
    ;
    @NonNull
    private Integer code;
    @NonNull
    private String msg;
}
