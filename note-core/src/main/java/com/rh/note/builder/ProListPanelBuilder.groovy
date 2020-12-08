package com.rh.note.builder

import com.rh.note.annatation.ProjectManage
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 项目管理窗口-最近打开项目列表
 */
@ProjectManage
class ProListPanelBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                preferredSize: [800, 500],
                layout: new BorderLayout(),
        ){
            children()
        }
    }

    static String id() {
        'project_list_panel'
    }
}
