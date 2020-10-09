package com.rh.note.event;


import com.rh.note.vo.ITitleLineVO;

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
        ITitleLineVO vo = operationAction().clickedNavigateButton(event);
        if (vo == null) {
            return;
        }
        workAction().openTextPaneByTitleNode(vo);
    }

}
