package com.rh.note.component;

import com.rh.note.base.BeanPath;

import javax.swing.JTextPane;

/**
 * 编辑面板
 * builder {@link com.rh.note.builder.TextPaneBuilder#id}
 * factory {@link com.rh.note.component.factory.AdocTextPaneFactory}
 */
public class AdocTextPane extends JTextPane {
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
