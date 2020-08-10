package com.rh.note.view;

import com.rh.note.constant.HintConstant;
import lombok.Getter;

import javax.swing.*;

/**
 * 弹窗口
 */
@Getter
public class InputWindow {

    private String defaultValue;

    public InputWindow(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 请求输入信息
     */
    public String getInputValue() {
        return JOptionPane.showInputDialog(HintConstant.ENTER_NEW_TITLE_NAME, defaultValue);
    }

}
