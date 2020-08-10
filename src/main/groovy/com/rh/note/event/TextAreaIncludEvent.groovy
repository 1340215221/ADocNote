package com.rh.note.event


import java.awt.event.KeyEvent
import java.util.function.Consumer

/**
 * 编辑区include语法
 */
class TextAreaIncludEvent implements Consumer<KeyEvent> {

    @Override
    void accept(KeyEvent event) {
        if (event.keyCode != 10 || event.modifiers != 0) {
            return
        }

    }

}
