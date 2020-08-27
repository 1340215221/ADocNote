package com.rh.note.builder

import com.rh.note.util.SwingComponent

import java.awt.*

/**
 * 基础面板
 */
class BasePanelBuilder implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                layout: new BorderLayout(),
                preferredSize: [900, 600],
//                background: Color.red,
        ){
            children()
        }
    }
    static String getId() {
        return 'base_panel'
    }
}
