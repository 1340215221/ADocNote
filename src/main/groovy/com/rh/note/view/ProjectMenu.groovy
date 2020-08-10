package com.rh.note.view

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout
import java.awt.Color

class ProjectMenu implements SwingComponent {

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                constraints: BorderLayout.CENTER,
                background: Color.green,
        ){
            children()
        }
    }

    static String getId() {
        'project_menu'
    }
}
