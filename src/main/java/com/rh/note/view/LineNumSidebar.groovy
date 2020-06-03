package com.rh.note.view

import com.rh.note.model.BoundEnum

import java.awt.Color

class LineNumSidebar implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        swingBuilder.panel(id: id,
                bounds: BoundEnum.line_num_sidebar.bound,
                background: Color.red,
                toolTipText: 'lineNumSidebar',
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'line_num_sidebar'
    }
}
