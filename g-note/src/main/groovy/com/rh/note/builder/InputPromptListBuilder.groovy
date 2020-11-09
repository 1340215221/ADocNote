package com.rh.note.builder

import com.rh.note.base.ISwingBuilder

/**
 * 输入提示列表
 */
class InputPromptListBuilder implements ISwingBuilder {

    void init(Closure children) {
        swingBuilder.popupMenu(id: id(),
        ) {
        }
    }

    static String id() {
        'input_prompt_list'
    }

}
