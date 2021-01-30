package com.rh.note.view;

import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.TextScrollPane;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * 获得没有被选择的编辑区
     */
    public @NotNull List<String> getFilePathOfTextPaneNotSelected() {
        Component[] arr = tabbedPane().getComponents();
        if (ArrayUtils.isEmpty(arr)) {
            return Collections.emptyList();
        }
        Component selected = tabbedPane().getSelectedComponent();
        return Arrays.stream(arr)
                .filter(c -> c instanceof TextScrollPane && c != selected)
                .map(scrollPane -> ((TextScrollPane) scrollPane).getBeanPath().getFilePath())
                .collect(Collectors.toList());
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

    /**
     * 获得被选择的编辑区对应的文件地址
     */
    public @Nullable String getFilePathOfTextPaneSelected() {
        Component scrollPane = tabbedPane().getSelectedComponent();
        if (!(scrollPane instanceof TextScrollPane)) {
            return null;
        }
        return ((TextScrollPane) scrollPane).getBeanPath().getFilePath();
    }

    /**
     * 存在被选择的编辑区
     */
    public boolean existSelected() {
        return tabbedPane().getSelectedIndex() >= 0;
    }

    /**
     * 判断存在没有被选择的编辑区
     */
    public boolean existNotSelected() {
        return tabbedPane().getComponentCount() > 1;
    }
}
