package com.rh.note.builder

import com.rh.note.base.ISwingBuilder
import com.rh.note.event.ImportProjectButtonEvent

/**
 * 项目管理窗口-导入项目按钮
 */
class ImportProButtonBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.button(id: id(),
                text: '打开项目',
                actionPerformed: {
                    ImportProjectButtonEvent.clicked_import_project_button()
                },
        ) {
            children()
        }
    }

    static String id() {
        'project_button'
    }
}
