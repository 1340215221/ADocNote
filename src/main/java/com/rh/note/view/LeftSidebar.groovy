package com.rh.note.view

import com.rh.note.model.BoundEnum

import java.awt.Color

class LeftSidebar implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        swingBuilder.panel(id: id,
                bounds: BoundEnum.left_sidebar.bound,
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
