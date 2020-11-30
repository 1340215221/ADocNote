package com.rh.note.builder

import com.rh.note.base.ISwingBuilder

import java.awt.BorderLayout

/**
 * 项目管理窗口-菜单面板
 */
class ProMenuPanelBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                constraints: BorderLayout.CENTER,
        ){
            children()
        }
    }

    static String id() {
        'project_menu'
    }
}
