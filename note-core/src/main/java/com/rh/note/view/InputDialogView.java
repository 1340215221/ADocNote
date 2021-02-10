package com.rh.note.view;

import com.rh.note.constants.PromptMessageEnum;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
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
    @Getter(lazy = true)
    private final String inputText = showInputDialog();

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
    private String showInputDialog() {
        String msg = Optional.ofNullable(this.msg).map(PromptMessageEnum::getValue).orElse(null);
        return JOptionPane.showInputDialog(msg, defaultValue);
    }

}
