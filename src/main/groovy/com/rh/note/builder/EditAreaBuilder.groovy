package com.rh.note.builder

import com.rh.note.util.SwingComponent

import java.awt.*

/**
 * 编辑区域
 */
class EditAreaBuilder implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.tabbedPane(id: id,
//                background: Color.cyan,
                constraints: BorderLayout.CENTER,
        ){
            children()
        }
    }

    static String getId() {
        'edit_area'
    }
}
