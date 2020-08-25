package com.rh.note.event


import com.rh.note.action.WorkAction
import com.rh.note.config.BeanConfig

import java.awt.event.MouseEvent

/**
 * 标题列表
 */
class TitleListEvent {

    private static final WorkAction workAction = BeanConfig.workAction

    /**
     * 打开标题内容
     */
    static def mouseClicked = { MouseEvent e ->
        workAction.openAdocFile()
    }

    /**
     * 显示或隐藏标题列表
     */
    static def hiddenOrShowTitleList = {
        workAction.hiddenOrShowTitleList()
    }

}
