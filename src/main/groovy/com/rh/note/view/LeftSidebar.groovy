package com.rh.note.view

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout
import java.awt.Color

/**
 * 左边栏
 */
class LeftSidebar implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                layout: new BorderLayout(),
                constraints: BorderLayout.WEST,
                background: Color.yellow,
        ){
            children()
        }
    }

    static String getId() {
        'left_sidebar'
    }
}
