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
    PARAMETER_ERROR(0, "参数错误"),
    UNKNOWN_BUSINESS_SITUATION_ERROR(1, "未知业务情况错误"),
    // 控件
    FAILED_TO_GET_BUILDER_THROUGH_GENERICS(1000, "通过泛型获取构建者失败"),
    FAILED_TO_GET_THE_BUILDER_CLASS_NAME(1001, "获取builder类名失败"),
    FAILED_TO_DESTROY_CONTROL(1002, "销毁控件失败"),
    FAILED_TO_INITIALIZE_EDIT_AREA_CONTENT(1003, "初始化编辑区内容失败"),
    // 语法
    THE_READ_ME_FILE_DOES_NOT_HAVE_A_FIRST_LEVEL_TITLE(2000, "该ReadMe文件没有一级标题"),
    ;
    @NonNull
    private Integer code;
    @NonNull
    private String msg;
}
