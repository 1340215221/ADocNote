package com.rh.note.builder;

import com.rh.note.base.ISwingBuilder
import com.rh.note.event.InputPromptListEvent

/**
 * 输入提示列表
 */
class InputPromptListBuilder implements ISwingBuilder {

    void init(Closure children) {
        swingBuilder.popupMenu(id: id(),
                menuKeyPressed: {
                    InputPromptListEvent.input_to_text_pane(it)
                },
        ) {
        }
    }

    static String id() {
        'input_prompt_list'
    }

}
