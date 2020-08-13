package com.rh.note.builder

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout
import java.awt.Color

/**
 * 左边栏
 */
class LeftSidebarBuilder implements SwingComponent {
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
