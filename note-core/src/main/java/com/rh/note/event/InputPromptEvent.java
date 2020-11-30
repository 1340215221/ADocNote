package com.rh.note.event;

import java.awt.event.MouseEvent;

import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 输入提示 事件
 */
public class InputPromptEvent {
    public static void selectItem(MouseEvent event) {
        workAction().replacePromptItem(event);
    }
}
