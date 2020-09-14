package com.rh.note.view;

import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.util.Init;

import javax.swing.*;

/**
 * 标题导航栏
 * {@link com.rh.note.builder.TabbedPaneBuilder#navigateId}
 */
public class TitleNavigatePanelView extends Init<JPanel> {

    public TitleNavigatePanelView init() {
        return super.init(TabbedPaneBuilder.navigateId());
    }

    /**
     * 添加
     */
    public void add(TitleNavigateButtonView titleNavigateButton) {
        if (titleNavigateButton == null) {
            return;
        }

        navigatePanel().add(titleNavigateButton.getBean());
    }

    /**
     * 清空
     */
    public void clearTitle() {
        navigatePanel().removeAll();
        navigatePanel().updateUI();
        navigatePanel().repaint();
    }

    private JPanel navigatePanel() {
        return getBean();
    }
}
