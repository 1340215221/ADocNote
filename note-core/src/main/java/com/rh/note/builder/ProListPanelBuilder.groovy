package com.rh.note.builder


import com.rh.note.annotation.ProManageSingleton
import com.rh.note.common.ISingletonBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import java.awt.BorderLayout

/**
 * 项目管理窗口-最近打开项目列表
 */
@ProManageSingleton(builder_name)
class ProListPanelBuilder implements ISingletonBuilder {

    static final String builder_name = "project_list_panel"
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

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    static String id() {
        'project_list_panel'
    }
}
