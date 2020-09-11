package com.rh.note.event;

import com.rh.note.component.TitleNavigateButton;

import java.awt.event.MouseEvent;

import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 导航按钮事件
 */
public class NavigateButtonEvent {

    /**
     * 点击导航按钮
     */
    public static void clicked_navigate_button(MouseEvent event) {
        Object source = event.getSource();
        if (!(source instanceof TitleNavigateButton)) {
            return;
        }
        workAction().clickedNavigateButton(((TitleNavigateButton) source).getTitleLine());
    }

}
