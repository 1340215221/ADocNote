package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 工作窗口-左边栏
 */
@WorkSingleton(LeftSidebarPanelBuilder.builder_name)
class LeftSidebarPanelBuilder implements ISingletonBuilder {

    static final String builder_name = "left_sidebar"
    @Autowired
    private SwingBuilder swingBuilder;

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                layout: new BorderLayout(),
                constraints: BorderLayout.WEST,
        ) {
            children()
        }
    }

    static String id() {
        'left_sidebar'
    }
}
