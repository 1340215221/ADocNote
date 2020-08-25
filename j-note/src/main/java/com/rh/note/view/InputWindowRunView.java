package com.rh.note.view;

import com.rh.note.constant.HintConstant;

import javax.swing.*;

/**
 * 输入弹窗口
 */
public class InputWindowRunView {

    /**
     * 默认输入值
     */
    private String defaultValue;

    public InputWindowRunView(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 请求输入信息
     */
    public String getInputValue() {
        return JOptionPane.showInputDialog(HintConstant.ENTER_NEW_TITLE_NAME, defaultValue);
    }

}
