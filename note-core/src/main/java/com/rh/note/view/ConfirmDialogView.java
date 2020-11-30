package com.rh.note.view;

import com.rh.note.constants.PromptMessageEnum;
import lombok.Getter;
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
    @Getter(lazy = true)
    private final boolean confirm = showConfirmDialog();
    /**
     * 提示信息
     */
    private PromptMessageEnum message;

    public @NotNull ConfirmDialogView init(@NonNull PromptMessageEnum message) {
        this.message = message;
        return this;
    }

    private boolean showConfirmDialog() {
        int result = JOptionPane.showConfirmDialog(null, message.getValue(), "确认信息", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

}
