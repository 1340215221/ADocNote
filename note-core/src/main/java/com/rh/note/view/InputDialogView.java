package com.rh.note.view;

import com.rh.note.constants.PromptMessageEnum;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JOptionPane;
import java.util.Optional;

/**
 * 输入弹窗
 */
public class InputDialogView {

    /**
     * 提示信息
     */
    private String defaultValue;
    /**
     * 提示信息
     */
    private PromptMessageEnum msg;
    /**
     * 输入文本
     */
    private String inputText;

    public @NotNull InputDialogView init(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public @NotNull InputDialogView init(String defaultValue, PromptMessageEnum msg) {
        this.defaultValue = defaultValue;
        this.msg = msg;
        return this;
    }

    /**
     * 弹窗获取用户输入值
     * 不重复请求值
     */
    private void showInputDialog() {
        if (StringUtils.isBlank(inputText)) {
            String msg = Optional.ofNullable(this.msg).map(PromptMessageEnum::getValue).orElse(null);
            inputText = JOptionPane.showInputDialog(msg, defaultValue);
        }
    }

    /**
     * 获得输入信息
     */
    public @Nullable String getInputText() {
        if (StringUtils.isBlank(inputText)) {
            showInputDialog();
        }
        return inputText;
    }

}
