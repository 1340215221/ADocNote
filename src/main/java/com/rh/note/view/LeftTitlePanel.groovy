package com.rh.note.view

class LeftTitlePanel implements SwingBuilderImpl {
    @Override
    def init(Closure run) {
        swingBuilder.panel(id: id,){
            run()
        }
    }

    @Override
    String getId() {
        return 'left_title_panel'
    }
}
