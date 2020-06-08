package com.rh.note.view

import com.rh.note.view.layout.BorderLayoutBuilder

import java.awt.*

class BasePanel implements SwingBuilderImpl {

    @Override
    init(Closure run) {
        def layout = {
            def builder = new BorderLayoutBuilder()
            run.delegate = builder
            run()
            builder.layout(this)
        }

        swingBuilder.panel(id: id,
                background: Color.red,
        ) {
            layout()
        }
    }

    @Override
    String getId() {
        return 'base_panel'
    }

}
