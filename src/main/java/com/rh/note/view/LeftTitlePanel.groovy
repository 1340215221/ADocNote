package com.rh.note.view

import java.awt.BorderLayout

class LeftTitlePanel implements SwingBuilderImpl {
    @Override
    def init(Closure run) {
        swingBuilder.panel(id: id,
                constraints: BorderLayout.CENTER,
                layout: new BorderLayout(),
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'left_title_panel'
    }
}
