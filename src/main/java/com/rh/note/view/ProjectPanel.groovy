package com.rh.note.view


import com.rh.note.view.layout.BorderLayoutBuilder

import java.awt.*

class ProjectPanel implements SwingBuilderImpl {
    @Override
    def init(Closure run) {
        def layout = {
            def builder = new BorderLayoutBuilder()
            run.delegate = builder
            run()
            builder.layout(this)
        }

        swingBuilder.panel(id: id,
                background: Color.red,
        ){
            layout()
        }
    }

    @Override
    String getId() {
        return 'project_panel'
    }
}
