package com.rh.note.view

import com.rh.note.event.TitleListEvent
import com.rh.note.util.SwingComponent

class FileListTitleButton implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.button(id: id,
                text: 'project',
                actionPerformed: TitleListEvent.hiddenOrShowTitleList,
        )
    }

    static String getId() {
        'file_list_title_button'
    }
}
