package com.rh.note.view


import javax.swing.*

class ProjectFrame implements SwingBuilderImpl {
    @Override
    def init(Closure run) {
        swingBuilder.frame(id: id,
                bounds: [500, 200, 800, 500],
                resizable: false,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'project_frame'
    }
}
