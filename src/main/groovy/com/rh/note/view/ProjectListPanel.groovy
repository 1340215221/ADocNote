package com.rh.note.view

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout
import java.awt.Color

class ProjectListPanel implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                background: Color.red,
                layout: new BorderLayout(),
        ){
            children()
        }
    }

    static String getId() {
        'project_list_panel'
    }
}
