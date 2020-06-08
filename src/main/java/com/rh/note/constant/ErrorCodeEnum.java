package com.rh.note.constant;

import lombok.Getter;

/**
 * 异常code
 */
@Getter
public enum ErrorCodeEnum {

    project_name_is_not_blank("项目名不能为空"),
    file_directory_creation_failed("文件目录创建失败"),
    file_creation_failed("文件创建失败"),
    file_read_failed("文件读取失败"),
    ;
    private String msg;

    ErrorCodeEnum(String msg) {
        this.msg = msg;
    }

}
