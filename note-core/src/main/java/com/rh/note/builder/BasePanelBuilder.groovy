package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 工作窗口-基础面板
 */
@WorkSingleton(BasePanelBuilder.builder_name)
class BasePanelBuilder implements ISingletonBuilder {

    static final builder_name = 'base_panel'
    @Autowired
    private SwingBuilder swingBuilder;

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                layout: new BorderLayout(),
                preferredSize: [900, 600],
        ){
            children()
        }
    }

    static String id() {
        return 'base_panel'
    }
}
