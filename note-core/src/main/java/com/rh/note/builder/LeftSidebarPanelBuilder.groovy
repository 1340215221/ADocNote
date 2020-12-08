package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 工作窗口-左边栏
 */
@WorkSingleton
class LeftSidebarPanelBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder;

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                layout: new BorderLayout(),
                constraints: BorderLayout.WEST,
        ){
            children()
        }
    }

    static String id() {
        'left_sidebar'
    }
}
