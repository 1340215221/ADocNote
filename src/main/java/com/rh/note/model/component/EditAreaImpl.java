package com.rh.note.model.component;

import com.rh.note.view.EditArea;

import javax.swing.*;
import java.awt.*;

/**
 * 编辑面板
 */
public class EditAreaImpl extends Init<JPanel> {

    public EditAreaImpl init() {
        return init(EditArea.getId());
    }

    private JPanel editArea() {
        return getBean();
    }

    /**
     * 获得布局
     */
    public LayoutManager getLayout() {
        return editArea().getLayout();
    }

    /**
     * 展示新添加或最后一个的控件
     */
    public void showLast() {
        LayoutManager layout = editArea().getLayout();
        if (!(layout instanceof CardLayout)) {
            return;
        }
        ((CardLayout) layout).last(editArea());

        if (editArea().getComponentCount() == 1) {
            editArea().validate();
            editArea().repaint();
        }
    }
}
