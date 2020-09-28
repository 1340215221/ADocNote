package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TabbedPaneBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.JTabbedPane;

/**
 * 编辑区标签面板视图
 * {@link TabbedPaneBuilder#id}
 */
public class TabbedPaneView extends Init<JTabbedPane> {

    public @NotNull TabbedPaneView init() {
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

    private @NotNull JTabbedPane tabbedPane() {
        return getBean();
    }

    /**
     * 显示指定编辑区
     */
    public void show(TextScrollPaneView textScrollPane) {
        if (textScrollPane == null) {
            return;
        }

        tabbedPane().setSelectedComponent(textScrollPane.getBean());
    }
}
