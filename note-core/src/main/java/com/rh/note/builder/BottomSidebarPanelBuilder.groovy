package com.rh.note.builder

import com.rh.note.base.ISwingBuilder

import java.awt.BorderLayout

/**
 * 工作窗口-底边栏
 */
class BottomSidebarPanelBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                bounds: [0,0,20,20],
                constraints: BorderLayout.SOUTH,
        ){
            children()
        }
    }

    static String id() {
        'bottom_sidebar'
    }

}
