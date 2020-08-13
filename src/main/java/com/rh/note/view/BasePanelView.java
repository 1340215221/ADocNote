package com.rh.note.view;

import com.rh.note.builder.BasePanelBuilder;
import com.rh.note.view.Init;

import javax.swing.*;

/**
 * 根面板
 */
public class BasePanelView extends Init<JPanel> {

    public BasePanelView init() {
        return super.init(BasePanelBuilder.getId());
    }

    /**
     * 刷新控件显示
     */
    public void refreshShow() {
        basePanel().validate();
        basePanel().repaint();
    }

    private JPanel basePanel() {
        return getBean();
    }
}
