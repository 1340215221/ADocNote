package com.rh.note.view;

import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.common.ISingletonView;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;

/**
 * 标题导航栏
 */
public class TitleNavigatePanelView extends ISingletonView<TabbedPaneBuilder, JPanel> {

    @Override
    public @NotNull TitleNavigatePanelView init() {
        return super.init();
    }

    /**
     * 添加
     */
    public void add(TitleNavigateButtonView titleNavigateButton) {
        if (titleNavigateButton == null) {
            return;
        }

        navigatePanel().add(titleNavigateButton.navigateButton());
    }

    private @NotNull JPanel navigatePanel() {
        return super.getComponent(TabbedPaneBuilder::getNavigatePanel);
    }

    /**
     * 刷新显示
     */
    public void refreshDisplay() {
        navigatePanel().updateUI();
        navigatePanel().repaint();
    }
}
