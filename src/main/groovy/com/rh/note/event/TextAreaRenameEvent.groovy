package com.rh.note.event

import com.rh.note.build.ActionBuild

import java.awt.event.KeyEvent
import java.util.function.Consumer

/**
 * 编辑区重命名
 * shift + F6
 */
class TextAreaRenameEvent implements Consumer<KeyEvent>, ActionBuild{
    @Override
    void accept(KeyEvent event) {
        if (event.keyCode != 117 || event.modifiers != 1) {
            return
        }

        workAction.rename(event.source.name)
    }
}
