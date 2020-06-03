package com.rh.note.view

import com.rh.note.model.BoundEnum

import java.awt.Color

class OpenFileTitle implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        swingBuilder.panel(id: id,
                bounds: BoundEnum.open_file_title.bound,
                background: Color.darkGray,
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'open_file_title'
    }
}
