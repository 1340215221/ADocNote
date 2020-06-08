package com.rh.note.view

import com.rh.note.model.MainViewBoundEnum
import com.rh.note.view.layout.BorderLayoutBuilder

import java.awt.CardLayout
import java.awt.Color

class EditArea implements SwingBuilderImpl {
    @Override
    init(Closure run) {
//        def layout = {
//            def builder = new BorderLayoutBuilder()
//            run.delegate = builder
//            run()
//            builder.layout(this)
//        }
        def layout = {
            swingBuilder.cardLayout(id: "${id}_layout"){}
        }

        swingBuilder.panel(id: id,
                bounds: MainViewBoundEnum.edit_area.bound,
                background: Color.cyan,
                layout: layout(),
        ) {
//            layout()
            run()
        }
    }

    @Override
    String getId() {
        return 'edit_area'
    }
}
