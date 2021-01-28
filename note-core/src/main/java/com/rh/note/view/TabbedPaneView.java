package com.rh.note.view;

import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.common.BaseView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 编辑区选项卡 视图
 */
public class TabbedPaneView extends BaseView<TabbedPaneBuilder, JTabbedPane> {

    public @NotNull TabbedPaneView init() {
        return super.init();
    }

    /**
     * 设置显示编辑区
     */
    public void show(TextScrollPaneView scrollPaneView) {
        if (scrollPaneView == null) {
            return;
        }
        tabbedPane().setSelectedComponent(scrollPaneView.scrollPane());
    }

    protected @NotNull JTabbedPane tabbedPane() {
        return super.getComponent(TabbedPaneBuilder::getTabbedPane);
    }

    /**
     * 添加打开文件
     */
    public void add(TextScrollPaneView scrollPaneView) {
        if (scrollPaneView == null) {
            return;
        }
        String fileName = scrollPaneView.scrollPane().getFileName();
        tabbedPane().add(scrollPaneView.scrollPane(), fileName);
    }
}
