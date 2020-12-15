package com.rh.note.view;

import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.common.ISingletonView;
import com.rh.note.component.TitleButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 清空
     */
    public void clearTitle() {
        navigatePanel().removeAll();
        navigatePanel().updateUI();
        navigatePanel().repaint();
    }

    private @NotNull JPanel navigatePanel() {
        return super.getComponent(TabbedPaneBuilder::getNavigatePanel);
    }

    /**
     * 获得包含的按钮
     */
    public @NotNull List<String> getAllChildrenBeanPath() {
        return Arrays.stream(navigatePanel().getComponents())
                .filter(c -> c instanceof TitleButton)
                .map(c -> ((TitleButton) c).getBeanPath().getBeanPath())
                .collect(Collectors.toList());
    }
}
