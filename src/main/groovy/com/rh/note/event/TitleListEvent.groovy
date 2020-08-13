package com.rh.note.event

import com.rh.note.build.ActionBuild
import com.rh.note.util.ISwingBuilder

import java.awt.event.MouseEvent

/**
 * 标题列表
 */
class TitleListEvent implements ISwingBuilder, ActionBuild {

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
