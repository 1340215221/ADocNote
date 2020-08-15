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
     * 展示编辑区,通过编辑区id
     */
    public void show(TextAreaScrollView textArea) {
        if (textArea == null) {
            return;
        }
        int index = editArea().indexOfComponent(textArea.getBean());
        if (index < 0) {
            return;
        }
        editArea().setSelectedIndex(index);
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
