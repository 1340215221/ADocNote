package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonStaticBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * 工作窗口-控制标题树显隐按钮
 */
@WorkSingleton(TitleTreeTabButtonBuilder.builder_name)
class TitleTreeTabButtonBuilder implements ISingletonStaticBuilder {

    static final String builder_name = "file_list_title_button"
    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.button(id: id,
                text: 'project',
        ) {
            children()
        }
    }

    @Override
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    static String getId() {
        'file_list_title_button'
    }
}
