package com.rh.note.view

import com.rh.note.util.SwingComponent

import java.awt.BorderLayout
import java.awt.Color

/**
 * 基础面板
 */
class BasePanel implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id,
                layout: new BorderLayout(),
                background: Color.red,
        ){
            children()
        }
    }
    static String getId() {
        return 'base_panel'
    }
}
