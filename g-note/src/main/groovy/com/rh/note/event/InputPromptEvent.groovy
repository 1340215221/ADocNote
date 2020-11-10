package com.rh.note.event

import java.awt.event.MouseEvent

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 输入提示 事件
 */
class InputPromptEvent {
    static void selectItem(MouseEvent event) {
        workAction().replacePromptItem(event)
    }
}
