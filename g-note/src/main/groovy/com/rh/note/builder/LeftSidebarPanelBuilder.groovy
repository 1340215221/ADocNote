package com.rh.note.builder

import com.rh.note.util.ISwingBuilder

import java.awt.BorderLayout

/**
 * 工作窗口-左边栏
 */
class LeftSidebarPanelBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                layout: new BorderLayout(),
                constraints: BorderLayout.WEST,
        ){
            children()
        }
    }

    static String id() {
        'left_sidebar'
    }
}
