package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 工作窗口-基础面板
 */
@WorkSingleton(id)
class BasePanelBuilder implements DefaultBuilder {

    /**
     * 根面板id
     */
    static final id = 'base_panel'
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
