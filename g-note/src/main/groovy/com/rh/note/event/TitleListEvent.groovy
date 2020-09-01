package com.rh.note.event

import com.rh.note.action.IWorkAction
import com.rh.note.config.ActionConfig

import java.awt.event.MouseEvent

/**
 * 标题列表
 */
class TitleListEvent {

    private static final IWorkAction workAction = ActionConfig.get.workAction()

    /**
     * 打开标题内容
     */
    static def mouseClicked = { MouseEvent e ->
        workAction.openAdocFile()
        workAction.loadTitleNavigate()
    }

    /**
     * 显示或隐藏标题列表
     */
    static def hiddenOrShowTitleList = {
        workAction.hiddenOrShowTitleList()
    }

}
