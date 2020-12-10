package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonStaticBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import java.awt.BorderLayout

/**
 * 工作窗口-左边栏-按钮面板
 */
@WorkSingleton(LeftSidebarTabPanelBuilder.builder_name)
class LeftSidebarTabPanelBuilder implements ISingletonStaticBuilder {

    static final String builder_name = "left_title_panel"
    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                constraints: BorderLayout.CENTER,
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
        'left_title_panel'
    }
}
