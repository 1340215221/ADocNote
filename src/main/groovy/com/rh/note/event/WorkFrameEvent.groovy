package com.rh.note.event

import java.awt.AWTEvent
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent

/**
 * 全局设置
 */
class WorkFrameEvent {

    /**
     * 全局保存
     */
    static AWTEventListener saveOperation = {AWTEvent event ->
        if (event.class != KeyEvent || event.ID != KeyEvent.KEY_PRESSED) {
            return
        }

        def keyEvent = event as KeyEvent
        if (keyEvent.keyCode == 83 && keyEvent.modifiers == 2) {
            //todo
            println 'saveOperation'
        }
    }

}
