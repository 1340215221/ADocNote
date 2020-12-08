package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 工作窗口-底边栏
 */
@WorkSingleton(id)
class BottomSidebarPanelBuilder implements DefaultBuilder {

    /**
     * 底边id
     */
    static final String id = 'bottom_sidebar'
    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                bounds: [0,0,20,20],
                constraints: BorderLayout.SOUTH,
        ){
            children()
        }
    }

    static String id() {
        'bottom_sidebar'
    }

}
