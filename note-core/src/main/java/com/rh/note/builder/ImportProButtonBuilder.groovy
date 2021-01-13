package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.event.ImportProjectButtonEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * 项目管理--导入项目按钮
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
class ImportProButtonBuilder {

    public static final String id = 'import_pro_button'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private ImportProjectButtonEvent event

    void init() {
        swingBuilder.button(id: id,
                text: '打开项目',
                actionPerformed: {
                    event.clicked_import_project_button()
                },
        ) {
        }
    }
}
