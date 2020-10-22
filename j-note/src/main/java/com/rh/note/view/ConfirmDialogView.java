package com.rh.note.view;

import com.rh.note.constants.PromptMessageEnum;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.swing.JOptionPane;

/**
 * 确定弹窗 视图
 */
public class ConfirmDialogView {

    /**
     * 确认结果
     */
    private Boolean result;
    /**
     * 提示信息
     */
    private PromptMessageEnum message;

    public @NotNull ConfirmDialogView init(@NonNull PromptMessageEnum message) {
        this.message = message;
        return this;
    }

    private void showConfirmDialog() {
        int result = JOptionPane.showConfirmDialog(null, message.getValue(), "确认信息", JOptionPane.YES_NO_OPTION);
        this.result = result == JOptionPane.YES_OPTION;
    }

    /**
     * 是否确认
     */
    public boolean isConfirm() {
        if (result == null) {
            showConfirmDialog();
        }
        return result;
    }

}
