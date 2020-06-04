package com.rh.note.view

import com.rh.note.listener.ProjectManageListener

class FileListTitleButton implements SwingBuilderImpl {
    @Override
    def init(Closure run) {
        swingBuilder.button(id: id,
                text: 'project',
                actionPerformed: ProjectManageListener.file_list_hidden_button,
        )
    }

    @Override
    String getId() {
        return 'file_list_title_button'
    }
}
