package com.rh.note.builder

import com.rh.note.annatation.ProjectManage
import com.rh.note.common.DefaultBuilder
import com.rh.note.event.ImportProjectButtonEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * 项目管理窗口-导入项目按钮
 */
@ProjectManage
class ImportProButtonBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private ImportProjectButtonEvent event

    @Override
    void init(Closure children) {
        swingBuilder.button(id: id(),
                text: '打开项目',
                actionPerformed: {
                    event.clicked_import_project_button()
                },
        ) {
            children()
        }
    }

    static String id() {
        'project_button'
    }
}
