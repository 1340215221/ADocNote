package com.rh.note.view;

import com.rh.note.constants.PromptMessageEnum;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 错误提示弹窗 视图
 */
public class ErrorMsgDialogView {
    /**
     * 提示信息
     */
    private PromptMessageEnum message;

    public @NotNull ErrorMsgDialogView init(@NonNull PromptMessageEnum message) {
        this.message = message;
        return this;
    }

    public void showConfirmDialog() {
        JOptionPane.showMessageDialog(null, message.getValue(), "自动同步消息", JOptionPane.ERROR_MESSAGE);
    }

}
