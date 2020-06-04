package com.rh.note.view

import com.rh.note.model.MainViewBoundEnum

import java.awt.Color

class BottomSidebar implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        swingBuilder.panel(id: id,
                bounds: MainViewBoundEnum.bottom_sidebar.bound,
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
