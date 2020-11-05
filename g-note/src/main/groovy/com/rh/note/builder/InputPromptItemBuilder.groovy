package com.rh.note.builder

import com.rh.note.ao.InputPromptItemAO
import com.rh.note.base.ISwingBuilder

/**
 * 输入提示项
 */
class InputPromptItemBuilder implements ISwingBuilder {

    private InputPromptItemAO ao

    InputPromptItemBuilder(InputPromptItemAO ao) {
        this.ao = ao
    }

    void init() {
        swingBuilder.menuItem(id: id(ao.getShowValue()),
                text: ao.getShowValue(),
                result: ao.getReplacementValue(),
        ) {
        }
    }

    static String id(String value) {
        "input_prompt_item_${value}"
    }

}
