package com.rh.note.event

import com.rh.note.action.IWorkAction
import com.rh.note.config.ActionConfig

import java.awt.*
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent

/**
 * 全局设置
 */
class WorkFrameEvent {

    private static IWorkAction workAction = ActionConfig.get.workAction()

    /**
     * 全局保存
     */
//    static AWTEventListener saveOperation = { AWTEvent event ->
//        if (event.class != KeyEvent || event.ID != KeyEvent.KEY_PRESSED) {
//            return
//        }
//
//        def keyEvent = event as KeyEvent
//        if (keyEvent.keyCode == 83 && keyEvent.modifiers == 2) {
//            workAction.saveAllEditContent()
//        }
//    }
    static def saveOperation = new AWTEventListener() {
        @Override
        void eventDispatched(AWTEvent event) {
            if (event.class != KeyEvent || event.ID != KeyEvent.KEY_PRESSED) {
                return
            }

            def keyEvent = event as KeyEvent
            if (keyEvent.keyCode == 83 && keyEvent.modifiers == 2) {
                workAction.saveAllEditContent()
            }
        }
    }


}
