package com.rh.note.builder

import com.rh.note.base.ISwingBuilder

import java.awt.BorderLayout


/**
 * 工作窗口-左边栏-按钮面板
 */
class LeftSidebarTabPanelBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                constraints: BorderLayout.CENTER,
                layout: new BorderLayout(),
        ){
            children()
        }
    }

    static String id() {
        'left_title_panel'
    }
}
