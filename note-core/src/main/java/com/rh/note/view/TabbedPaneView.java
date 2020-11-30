package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.component.AdocScrollPane;
import com.rh.note.path.AdocFileBeanPath;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JTabbedPane;
import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 编辑区标签面板视图
 */
public class TabbedPaneView extends Init<JTabbedPane> {

    public @NotNull TabbedPaneView init() {
        return super.init(TabbedPaneBuilder.id());
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

    /**
     * 显示java文件编辑区
     */
    public void show(JavaScrollPaneView scrollPane) {
        if (scrollPane == null) {
            return;
        }
        tabbedPane().setSelectedComponent(scrollPane.getBean());
    }

    /**
     * 添加java文件滚动面板
     */
    public void add(JavaScrollPaneView scrollPane) {
        if (scrollPane == null) {
            return;
        }
        String fileName = scrollPane.getBean().getFileName();
        tabbedPane().add(scrollPane.getBean(), fileName);
    }

    /**
     * 添加编辑文件
     */
    public void add(TextScrollPaneView textScrollPane) {
        if (textScrollPane == null) {
            return;
        }
        String titleName = textScrollPane.getBean().getBeanPath().getTitleName();
        tabbedPane().add(textScrollPane.getBean(), titleName);
    }

    /**
     * 获得被选择的面板
     */
    public @Nullable TextScrollPaneView getSelectedTextPane() {
        Component component = tabbedPane().getSelectedComponent();
        return TextScrollPaneView.cast(component);
    }

    /**
     * 获得所有打开的文件的文件路径
     */
    public @NotNull List<String> getAllFilePathOfExistFile() {
        Component[] components = tabbedPane().getComponents();
        if (ArrayUtils.isEmpty(components)) {
            return Collections.emptyList();
        }
        return Arrays.stream(components)
                .filter(c -> c instanceof AdocScrollPane)
                .map(c -> ((AdocScrollPane) c))
                .map(scrollPane -> ((AdocFileBeanPath) scrollPane.getBeanPath()).getFilePath())
                .collect(Collectors.toList());
    }

    /**
     * 判断是否包含编辑区
     */
    public boolean contains(TextScrollPaneView textScrollPane) {
        if (textScrollPane == null) {
            return false;
        }

        int index = tabbedPane().indexOfComponent(textScrollPane.getBean());
        return index > -1;
    }

    /**
     * 删除控件
     */
    public void remove(TextScrollPaneView textScrollPane) {
        if (textScrollPane == null) {
            return;
        }

        tabbedPane().remove(textScrollPane.getBean());
    }
}
