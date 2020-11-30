package com.rh.note.builder

import com.rh.note.base.ISwingBuilder

import java.awt.BorderLayout

class ProListPanelBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                preferredSize: [800, 500],
                layout: new BorderLayout(),
        ){
            children()
        }
    }

    static String id() {
        'project_list_panel'
    }
}
