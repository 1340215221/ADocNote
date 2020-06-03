package com.rh.note.view

import com.rh.note.model.BoundEnum

import java.awt.Color

class HeadMenu implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        swingBuilder.panel(id: id,
                bounds: BoundEnum.head_menu.bound,
                background: Color.green,
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'head_menu'
    }
}
