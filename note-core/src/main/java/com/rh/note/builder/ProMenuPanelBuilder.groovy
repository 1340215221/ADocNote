package com.rh.note.builder

import com.rh.note.annatation.ProjectManage
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 项目管理窗口-菜单面板
 */
@ProjectManage
class ProMenuPanelBuilder implements DefaultBuilder {

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

    static String id() {
        'project_menu'
    }
}
