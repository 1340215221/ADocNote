package com.rh.note.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 * 输入提示菜单项
 */
public class InputPromptMenuItem extends JMenuItem {
    /**
     * 替换结果
     */
    private String result;

    public InputPromptMenuItem() {
        super();
    }

    public InputPromptMenuItem(Icon icon) {
        super(icon);
    }

    public InputPromptMenuItem(String text) {
        super(text);
    }

    public InputPromptMenuItem(Action a) {
        super(a);
    }

    public InputPromptMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    public InputPromptMenuItem(String text, int mnemonic) {
        super(text, mnemonic);
    }

    public String getResult() {
        return result;
    }
}
