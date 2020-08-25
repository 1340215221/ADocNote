package com.rh.note.builder

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout

class LeftTitlePanelBuilder implements SwingComponent {
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
