package com.rh.note.view;

import com.rh.note.base.ITitleBeanPath;
import com.rh.note.base.Init;
import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.component.AdocScrollPane;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.vo.WriterVO;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JTabbedPane;
import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 编辑区标签面板视图
 */
public class TabbedPaneView extends Init<JTabbedPane> {

    public @NotNull TabbedPaneView init() {
        return super.init(TabbedPaneBuilder.id());
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
}
