package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * 输入提示列表
 */
@WorkSingleton(id)
class InputPromptListBuilder implements DefaultBuilder {

    /**
     * 输入提示列表id
     */
    static final String id = 'input_prompt_list'
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

    static String id() {
        'input_prompt_list'
    }

}
