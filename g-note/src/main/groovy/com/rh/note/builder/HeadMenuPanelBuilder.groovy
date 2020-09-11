package com.rh.note.builder

import com.rh.note.util.ISwingBuilder

import java.awt.BorderLayout

/**
 * 工作窗口-头部菜单栏
 */
class HeadMenuPanelBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                constraints: BorderLayout.NORTH,
        ){
            children()
        }
    }

    static String id() {
        'head_menu'
    }
}
