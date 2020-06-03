package com.rh.note.view

import com.rh.note.model.BoundEnum
import com.rh.note.view.layout.BorderLayoutBuilder

import java.awt.Color

class EditArea implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        def layout = {
            def builder = new BorderLayoutBuilder()
            run.delegate = builder
            run()
            builder.layout(this)
        }

        swingBuilder.panel(id: id,
                bounds: BoundEnum.edit_area.bound,
                background: Color.cyan,
        ) {
            layout()
        }
    }

    @Override
    String getId() {
        return 'edit_area'
    }
}
