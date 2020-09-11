package com.rh.note.event;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;

import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 工作窗口 全局事件
 */
public class WorkFrameEvent {

    /**
     * 保存已打开文件内容
     */
    public static void saveAllEdited(AWTEvent event) {
        if (!(event instanceof KeyEvent) || event.getID() != KeyEvent.KEY_PRESSED
                || (((KeyEvent) event).getKeyCode() != 83 || ((KeyEvent) event).getModifiers() != 2)
        ) {
            return;
        }
        workAction().saveAllEdited();
    }

}
