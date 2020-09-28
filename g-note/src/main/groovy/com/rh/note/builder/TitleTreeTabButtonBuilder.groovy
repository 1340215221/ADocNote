package com.rh.note.builder

import com.rh.note.base.ISwingBuilder


/**
 * 工作窗口-控制标题树显隐按钮
 */
class TitleTreeTabButtonBuilder implements ISwingBuilder {
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
