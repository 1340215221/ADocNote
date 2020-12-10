package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.ao.InputPromptItemAO
import com.rh.note.common.ISingletonBuilder
import com.rh.note.event.InputPromptEvent
import groovy.swing.SwingBuilder
import org.jetbrains.annotations.Nullable
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy

/**
 * 输入提示项
 */
@WorkSingleton(builder_name)
class InputPromptItemBuilder implements ISingletonBuilder {

    static final String builder_name = 'input_prompt_item_{}'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private InputPromptEvent event
    private InputPromptItemAO ao

    InputPromptItemBuilder(InputPromptItemAO ao) {
        this.ao = ao
    }

    /**
     * todo 动态创建的单例对象, 不需要子控件参数 children
     */
    void init(@Nullable Closure children) {
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

    static String id(String value) {
        "input_prompt_item_${value}"
    }

}
