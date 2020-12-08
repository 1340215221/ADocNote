package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * 工作窗口-控制标题树显隐按钮
 */
@WorkSingleton
class TitleTreeTabButtonBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.button(id: id,
                text: 'project',
        ){
            children()
        }
    }

    static String getId() {
        'file_list_title_button'
    }
}
