package com.rh.note.event

import com.rh.note.build.ActionBuild

import java.awt.event.KeyEvent

/**
 * 编辑区事件
 */
class TextAreaEvent implements ActionBuild {

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