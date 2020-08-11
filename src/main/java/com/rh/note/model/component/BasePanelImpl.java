package com.rh.note.model.component;

import com.rh.note.view.BasePanel;

import javax.swing.*;

/**
 * 根面板
 */
public class BasePanelImpl extends Init<JPanel> {

    public BasePanelImpl init() {
        return super.init(BasePanel.getId());
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
