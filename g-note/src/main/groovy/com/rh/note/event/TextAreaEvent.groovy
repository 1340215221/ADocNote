package com.rh.note.event

import com.rh.note.action.IWorkAction
import com.rh.note.config.ActionConfig
import org.apache.commons.lang3.StringUtils

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

/**
 * 编辑区事件
 */
class TextAreaEvent {

    private static final IWorkAction workAction = ActionConfig.get.workAction()

    /**
     * include语法块生成
     */
    static def generateIncludeBlock = { String componentId ->
        if (StringUtils.isBlank(componentId) || !workAction.isIncludeGrammarLine(componentId)) {
            workAction.insertEnter(componentId)
            return
        }
        workAction.generateIncludeBlock(componentId)
    }

    /**
     * 重命名include
     */
    static def rename = {KeyEvent event ->
        if (event.keyCode == 117 && event.modifiers == 1) {
            workAction.rename(event.source.name)
        }
    }

    static def deleteInclude = { ActionEvent e ->
        workAction.deleteInclude(e)
    }
}
