package com.rh.note.builder


import com.rh.note.annotation.ProManageSingleton
import com.rh.note.common.ISingletonStaticBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 项目管理窗口-菜单面板
 */
@ProManageSingleton(ProMenuPanelBuilder.builder_name)
class ProMenuPanelBuilder implements ISingletonStaticBuilder {

    static final String builder_name = "project_menu"
    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                constraints: BorderLayout.CENTER,
        ){
            children()
        }
    }

    @Override
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    static String id() {
        'project_menu'
    }
}
