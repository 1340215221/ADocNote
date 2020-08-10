package com.rh.note.view

import com.rh.note.build.ActionBuild
import com.rh.note.util.SwingComponent

class FileListTitleButton implements SwingComponent, ActionBuild {
    @Override
    void init(Closure children) {
        swingBuilder.button(id: id,
                text: 'project',
                actionPerformed: workAction.hiddenOrShowTitleList,
        )
    }

    static String getId() {
        'file_list_title_button'
    }
}
