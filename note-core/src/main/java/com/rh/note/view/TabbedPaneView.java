package com.rh.note.view;

import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.TextScrollPane;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 获得编辑控件的文件路径
     */
    public @NotNull List<String> getFilePathsOfTextPane() {
        Component[] arr = tabbedPane().getComponents();
        if (ArrayUtils.isEmpty(arr)) {
            return Collections.emptyList();
        }
        return Arrays.stream(arr)
                .filter(c -> c instanceof TextScrollPane)
                .map(scrollPane -> ((TextScrollPane) scrollPane).getBeanPath().getFilePath())
                .collect(Collectors.toList());
    }
}
