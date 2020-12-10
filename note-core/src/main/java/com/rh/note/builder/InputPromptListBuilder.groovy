package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy

/**
 * 输入提示列表
 */
@WorkSingleton(builder_name)
class InputPromptListBuilder implements ISingletonBuilder {

    /**
     * 输入提示列表id
     */
    static final String builder_name = 'input_prompt_list'
    @Autowired
    private SwingBuilder swingBuilder;

    @Override
    void init(Closure children) {
        swingBuilder.popupMenu(id: id(),
                borderPainted: false,
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
        'input_prompt_list'
    }

}