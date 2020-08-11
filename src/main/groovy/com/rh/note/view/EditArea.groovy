package com.rh.note.view

import com.rh.note.util.SwingComponent

import java.awt.*

/**
 * 编辑区域
 */
class EditArea implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                background: Color.cyan,
                constraints: BorderLayout.CENTER,
                layout: new CardLayout(),
        ) {
            children()
        }
    }

    static String getId() {
        'edit_area'
    }

    static String getLayoutId() {
        'edit_area_layout'
    }
}
