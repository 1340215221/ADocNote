package com.rh.note.component;

import com.rh.note.base.BeanPath;
import com.rh.note.builder.TextPaneBuilder;

import javax.swing.JScrollPane;

/**
 * 编辑区-滚动面板
 * builder {@link TextPaneBuilder#scrollId}
 * factory {@link com.rh.note.component.factory.AdocScrollPaneFactory}
 */
public class AdocScrollPane extends JScrollPane {

    public static final String NAME = "tScrollPane";
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
