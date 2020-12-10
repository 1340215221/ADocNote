package com.rh.note.builder

import com.rh.note.annotation.WorkPrototype
import com.rh.note.ao.InputPromptItemAO
import com.rh.note.common.IPrototypeBuilder
import com.rh.note.component.InputPromptMenuItem
import com.rh.note.event.InputPromptEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy

/**
 * 输入提示项
 */
@WorkPrototype(InputPromptItemBuilder.builder_name)
class InputPromptItemBuilder implements IPrototypeBuilder {

    static final String builder_name = 'input_prompt_item_{}'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private InputPromptEvent event
    private InputPromptItemAO ao

    InputPromptItemBuilder(InputPromptItemAO ao) {
        this.ao = ao
    }

    @Override
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

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    @Override
    String getInstanceName() {
        return id(ao.getCompleteValue())
    }

    static String id(String value) {
        "input_prompt_item_${value}"
    }

    InputPromptMenuItem getMenuItem() {
        return swingBuilder."${id(ao.getCompleteValue())}"
    }
}
