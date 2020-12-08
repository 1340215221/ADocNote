package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.ao.InputPromptItemAO
import com.rh.note.event.InputPromptEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * 输入提示项
 */
@WorkSingleton(id)
class InputPromptItemBuilder {

    /**
     * 输入提示项id
     */
    static final String id = 'input_prompt_item_{}'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private InputPromptEvent event
    private InputPromptItemAO ao

    InputPromptItemBuilder(InputPromptItemAO ao) {
        this.ao = ao
    }

    void init() {
        swingBuilder.menuItem(id: id(ao.getCompleteValue()),
                text: ao.getCompleteValue(),
                result: ao.getDescription(),
                mousePressed: {
                    event.selectItem(it)
                },
        ) {
        }
    }

    static String id(String value) {
        "input_prompt_item_${value}"
    }

}
