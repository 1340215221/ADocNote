package com.rh.note.view;

import com.rh.note.builder.EditAreaBuilder;

import javax.swing.*;

/**
 * 编辑面板
 */
public class EditAreaView extends Init<JTabbedPane> {

    public EditAreaView init() {
        return init(EditAreaBuilder.getId());
    }

    protected JTabbedPane editArea() {
        return getBean();
    }

    /**
     * 展示指定控件
     */
    public void show(TextPaneScrollView textPaneScroll) {
        editArea().setSelectedComponent(textPaneScroll.getBean());
    }

    /**
     * 展示新添加或最后一个的控件
     * todo
     */
    public void showLast() {
        int tabCount = editArea().getTabCount();
        editArea().setSelectedIndex(tabCount - 1);
    }
}
