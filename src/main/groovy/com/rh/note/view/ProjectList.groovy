package com.rh.note.view

import com.rh.note.event.ProjectListEvent
import com.rh.note.util.SwingComponent

import java.awt.BorderLayout

class ProjectList implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.list(id: id,
                fixedCellHeight:50,
                fixedCellWidth: 300,
                constraints: BorderLayout.WEST,
                mouseClicked: ProjectListEvent.project_list_mouse_client,
        ){
            children()
        }
    }

    static String getId() {
        'project_list'
    }
}
