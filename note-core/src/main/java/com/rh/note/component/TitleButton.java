package com.rh.note.component;

import com.rh.note.base.BeanPath;
import com.rh.note.builder.TitleNavigateButtonBuilder;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * 标题按钮
 */
public class TitleButton extends JButton {
    /**
     * 对象地址
     */
    private BeanPath beanPath;

    public TitleButton() {
        super();
    }

    public TitleButton(Icon icon) {
        super(icon);
    }

    public TitleButton(String text) {
        super(text);
    }

    public TitleButton(Action a) {
        super(a);
    }

    public TitleButton(String text, Icon icon) {
        super(text, icon);
    }

    public BeanPath getBeanPath() {
        return beanPath;
    }

    public void setBeanPath(BeanPath beanPath) {
        this.beanPath = beanPath;
    }
}
