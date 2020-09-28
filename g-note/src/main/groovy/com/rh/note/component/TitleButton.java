package com.rh.note.component;

import com.rh.note.base.BeanPath;

import javax.swing.JButton;

/**
 * 标题按钮
 * builder {@link com.rh.note.builder.TitleNavigateButtonBuilder#id}
 * factory {@link com.rh.note.component.factory.TitleButtonFactory}
 */
public class TitleButton extends JButton {

    /**
     * 对象地址
     */
    private BeanPath beanPath;

    public BeanPath getBeanPath() {
        return beanPath;
    }

    public void setBeanPath(BeanPath beanPath) {
        this.beanPath = beanPath;
    }
}
