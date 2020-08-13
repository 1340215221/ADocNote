package com.rh.note.builder

import com.rh.note.event.ProjectManagerMenuEvent
import com.rh.note.util.SwingComponent

/**
 * 项目选择菜单按钮
 */
class OpenProjectButtonBuilder implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.button(id: id,
                text: '打开项目',
                actionPerformed: ProjectManagerMenuEvent.import_project,
        ) {
            children()
        }
    }

    static String getId() {
        'project_button'
    }
}
