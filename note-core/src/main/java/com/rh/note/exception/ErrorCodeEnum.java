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
    NOT_ALLOWED_TO_OPERATE_TWO_CONTAINER_CONTROLS_IN_ONE_THREAD(1004, "不允许在一个线程内操作两个容器的控件"),
    // 语法
    STOP_FIND_ROOT_TITLE(2000, "停止查找根标题"),
    IS_NOT_SYNTAX_SUGAR_LINE(2001, "不是快捷语法行"),
    // 编辑区
    FAILED_TO_WRITE_THE_CONTENT_OF_THE_EDIT_AREA_TO_THE_FILE(3000, "编辑区内容写入文件失败"),
    FAILED_TO_GET_THE_CONTENT_OF_THE_EDIT_AREA(3001, "获得编辑区行内容失败"),
    FAILED_TO_UPDATE_FILE_ROOT_TITLE_NAME(3002, "更新文件根标题名字失败"),
    // 文件
    FAILED_TO_CREATE_ADOC_FILE(4000, "创建adoc文件失败"),
    // git
    GIT_OPERATION_OBJECT_CREATION_FAILED(5000, "git操作对象创建失败"),
    GIT_ADD_OPERATION_FAILED(5001, "git add 操作失败"),
    COMMIT_OPERATION_FAILED(5002, "commit 操作失败"),
    PULL_OPERATION_FAILED(5003, "pull 操作失败"),
    CANCEL_OPEN_PROJECT(5004, "git同步失败, 取消打开项目"),
    GIT_MERGE_RECOVERY_FAILED(5005, "git合并恢复失败, 取消打开项目"),
    GIT_PUSH_FAILED(5006, "git推送失败, 取消打开项目"),
    ;
    @NonNull
    private Integer code;
    @NonNull
    private String msg;
}
