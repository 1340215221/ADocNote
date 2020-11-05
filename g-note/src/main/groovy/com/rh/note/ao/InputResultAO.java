package com.rh.note.ao;

import com.rh.note.component.AdocTextPane;

/**
 * 输入值 参数
 */
public class InputResultAO {
    /**
     * 输入值
     */
    private String value;
    /**
     * 编辑控件
     */
    private AdocTextPane textPane;

    public String getValue() {
        return value;
    }

    public InputResultAO setValue(String value) {
        this.value = value;
        return this;
    }

    public InputResultAO copy(AdocTextPane textPane) {
        if (textPane != null) {
            this.textPane = textPane;
        }
        return this;
    }
}
