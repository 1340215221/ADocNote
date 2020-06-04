package com.rh.note.view

import com.rh.note.listener.ProjectManageListener

class ProjectList implements SwingBuilderImpl {
    @Override
    def init(Closure run) {
        swingBuilder.list(id: id,
                fixedCellHeight:50,
                fixedCellWidth: 300,
//                cellRenderer:new StripeRenderer(),
                mouseClicked: ProjectManageListener.project_list_mouse_client,
        ){
            run()
        }
    }

    @Override
    String getId() {
        return 'project_list'
    }
}
