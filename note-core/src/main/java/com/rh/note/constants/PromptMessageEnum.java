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
    are_you_sure_you_want_to_delete_safely("您确定要安全删除"),
    are_you_sure_you_want_to_delete_safely_include("您确定要安全删除 Include 相关内容"),
    GIT_SUBMITTED_SUCCESSFULLY("git提交成功"),
    AUTO_SAVE_SUCCESSFULLY("自动保存成功"),
    ;
    @NonNull
    private String value;


}
