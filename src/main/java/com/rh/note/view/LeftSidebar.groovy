package com.rh.note.view

import com.rh.note.model.MainViewBoundEnum

import java.awt.BorderLayout
import java.awt.Color

class LeftSidebar implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        swingBuilder.panel(id: id,
                layout: new BorderLayout(),
                background: Color.yellow,
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'left_sidebar'
    }
}
