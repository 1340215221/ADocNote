package com.rh.note.model.component;

import com.rh.note.view.EditArea;
import org.apache.commons.lang3.StringUtils;

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
     * 展示编辑区,通过编辑区id
     */
    public void show(String cardId) {
        if (StringUtils.isBlank(cardId)) {
            return;
        }

        LayoutManager layout = editArea().getLayout();
        if (!(layout instanceof CardLayout)) {
            return;
        }
        ((CardLayout) layout).show(editArea(), cardId);
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
