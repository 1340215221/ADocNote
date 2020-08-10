package com.rh.note.view

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout

class LeftTitlePanel implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                constraints: BorderLayout.CENTER,
                layout: new BorderLayout(),
        ){
            children()
        }
    }

    static String getId() {
        'left_title_panel'
    }
}
