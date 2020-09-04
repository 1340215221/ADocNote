package com.rh.note.event

import com.rh.note.action.IWorkAction
import com.rh.note.config.ActionConfig

import java.awt.*
import java.awt.event.KeyEvent

/**
 * 全局设置
 */
class WorkFrameEvent {

    private static IWorkAction workAction = ActionConfig.get.workAction()

    /**
     * 关闭全部标签
     */
    static def closeAllTab = { AWTEvent event ->
        //todo
    }

    /**
     * 关闭当前标签
     */
    static def closeCurrentTab = { AWTEvent event ->
        //todo
    }

    /**
     * 关闭左侧标签
     */
    static def closeLeftTab = { AWTEvent event ->
        //todo
    }

    /**
     * 关闭右侧标签
     */
    static def closeRightTab = { AWTEvent event ->
        //todo
    }

    /**
     * 全局保存
     */
    static def saveOperation = {AWTEvent event ->
        if (event.class != KeyEvent || event.ID != KeyEvent.KEY_PRESSED) {
            return
        }

        def keyEvent = event as KeyEvent
        if (keyEvent.keyCode == 83 && keyEvent.modifiers == 2) {
            workAction.saveAllEditContent()
        }
    }

}
