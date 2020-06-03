package com.rh.note.view

import com.rh.note.model.BoundEnum

import java.awt.Color

class BottomSidebar implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        swingBuilder.panel(id: id,
                bounds: BoundEnum.bottom_sidebar.bound,
                background: Color.pink,
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'bottom_sidebar'
    }
}
