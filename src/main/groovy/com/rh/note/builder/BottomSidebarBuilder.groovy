package com.rh.note.builder

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout
import java.awt.Color

class BottomSidebarBuilder implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                bounds: [0,0,20,20],
                constraints: BorderLayout.SOUTH,
                background: Color.pink,
        ){
            children()
        }
    }

    static String getId() {
        'bottom_sidebar'
    }

}
