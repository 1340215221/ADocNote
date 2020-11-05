package com.rh.note.component;

import javax.swing.JMenuItem;

/**
 * 输入提示菜单项
 */
public class InputPromptMenuItem extends JMenuItem {

    public static final String NAME = "menuItem";
    /**
     * 替换结果
     */
    private String result;

    public InputPromptMenuItem(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
