package com.rh.note.builder


import com.rh.note.annotation.ProManageSingleton
import com.rh.note.common.ISingletonStaticBuilder
import com.rh.note.event.ImportProjectButtonEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy

/**
 * 项目管理窗口-导入项目按钮
 */
@ProManageSingleton
class ImportProButtonBuilder implements ISingletonStaticBuilder {

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

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    static String id() {
        'project_button'
    }
}
