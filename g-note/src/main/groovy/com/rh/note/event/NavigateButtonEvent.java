package com.rh.note.event;

import com.rh.note.bean.IAdocFile;
import com.rh.note.component.TitleNavigateButton;

import java.awt.event.MouseEvent;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
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
        IAdocFile adocFile = operationAction().getAdocFileByTitle(((TitleNavigateButton) source).getTitleLine());
        if (adocFile == null) {
            return;
        }
        workAction().openTextPaneByAdocFile(adocFile);
        workAction().openTextPaneByTitle(((TitleNavigateButton) source).getTitleLine());
    }

}
