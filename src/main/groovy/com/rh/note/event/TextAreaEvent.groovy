package com.rh.note.event


import com.rh.note.action.WorkAction
import com.rh.note.config.BeanConfig

import java.awt.event.KeyEvent

/**
 * 编辑区事件
 */
class TextAreaEvent {

    private static final WorkAction workAction = BeanConfig.workAction

    /**
     * include语法块生成
     */
    static def generateIncludeBlock = {KeyEvent event ->
        if (event.keyCode == 10 && event.modifiers == 0) {
            workAction.generateIncludeBlock(event.source.name)
        }
    }

    /**
     * 重命名include
     */
    static def rename = {KeyEvent event ->
        if (event.keyCode == 117 && event.modifiers == 1) {
            workAction.rename(event.source.name)
        }
    }

}
