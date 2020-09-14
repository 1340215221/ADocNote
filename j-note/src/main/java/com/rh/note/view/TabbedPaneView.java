package com.rh.note.view;

import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.util.Init;

import javax.swing.JTabbedPane;

/**
 * 编辑区标签面板视图
 * {@link com.rh.note.builder.TabbedPaneBuilder#id}
 */
public class TabbedPaneView extends Init<JTabbedPane> {

    public TabbedPaneView init() {
        return super.init(TabbedPaneBuilder.id());
    }

    /**
     * 添加编辑文件
     */
    public void add(TextScrollPaneView textScrollPane, String fileName) {
        if (textScrollPane == null) {
            return;
        }
        tabbedPane().add(textScrollPane.getBean(), fileName);
    }

    private JTabbedPane tabbedPane() {
        return getBean();
    }

    /**
     * 显示指定编辑区
     */
    public void show(TextPaneView textPane) {
        if (textPane == null) {
            return;
        }

        tabbedPane().setSelectedComponent(textPane.getBean());
    }
}
