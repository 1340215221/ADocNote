package com.rh.note.builder

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout
import java.awt.Color

class ProjectMenuBuilder implements SwingComponent {

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                constraints: BorderLayout.CENTER,
//                background: Color.green,
        ){
            children()
        }
    }

    static String getId() {
        'project_menu'
    }
}
