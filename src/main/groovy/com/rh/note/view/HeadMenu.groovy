package com.rh.note.view

import com.rh.note.util.SwingComponent

import java.awt.*

/**
 * 头部菜单栏
 */
class HeadMenu implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                constraints: BorderLayout.NORTH,
                background: Color.green,
        ){
            children()
        }
    }

    static String getId() {
        'head_menu'
    }
}
