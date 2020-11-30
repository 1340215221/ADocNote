package com.rh.note.builder

import com.rh.note.base.ISwingBuilder

import java.awt.BorderLayout

/**
 * 工作窗口-基础面板
 */
class BasePanelBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                layout: new BorderLayout(),
                preferredSize: [900, 600],
        ){
            children()
        }
    }
    static String id() {
        return 'base_panel'
    }
}
