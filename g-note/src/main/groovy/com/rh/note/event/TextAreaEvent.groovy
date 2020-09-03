package com.rh.note.event

import com.rh.note.action.IWorkAction
import com.rh.note.common.ActionResultContext
import com.rh.note.config.ActionConfig

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

/**
 * 编辑区事件
 */
class TextAreaEvent {

    private static final IWorkAction workAction = ActionConfig.get.workAction()

    /**
     * 下沉标题
     */
    static def sinkTitle = { KeyEvent event ->
        if (event.keyCode == 117 && event.modifiers == 0) {
//            workAction.sinkTitle(event.source.name) // todo
        }
    }

    /**
     * 重命名include
     */
    static def rename = { KeyEvent event ->
        if (event.keyCode == 117 && event.modifiers == 1) {
            workAction.rename(event.source.name)
        }
    }

    static def deleteInclude = { ActionEvent e ->
        workAction.deleteInclude(e)
    }

    /**
     * 回车操作事件
     */
    static def enter = { ActionEvent e ->
        def componentId = e.source.name as String
        if (workAction.generateIncludeBlock(componentId) || operationSuccess()) {
            return
        }
        if (workAction.generateTableBlock(componentId) || operationSuccess()) {
            return
        }
        workAction.insertEnter(e)
    }

    /**
     * 判断替换语法是否已经操作成功
     */
    private static boolean operationSuccess() {
        def result = new ActionResultContext().getResult()
        return result != null && Objects.equals(200, result.getCode())
    }
}
