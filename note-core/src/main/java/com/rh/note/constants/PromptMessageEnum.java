package com.rh.note.constants;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 提示信息
 */
@Getter
@RequiredArgsConstructor
public enum PromptMessageEnum {

    rename_include_message("请输入新的标签名"),
    ;
    @NonNull
    private String value;


}
