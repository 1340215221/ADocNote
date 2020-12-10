package com.rh.note.component;

import com.rh.note.base.BeanPath;
import com.rh.note.builder.TextPaneBuilder;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 * 编辑面板
 * builder {@link TextPaneBuilder#builder_name}
 */
public class AdocTextPane extends JTextPane {
    public static final String NAME = "textPane";
    /**
     * 对象地址
     */
    private BeanPath beanPath;

    public AdocTextPane() {
        super();
    }

    public AdocTextPane(StyledDocument doc) {
        super(doc);
    }

    public BeanPath getBeanPath() {
        return beanPath;
    }

    public void setBeanPath(BeanPath beanPath) {
        this.beanPath = beanPath;
    }
}
