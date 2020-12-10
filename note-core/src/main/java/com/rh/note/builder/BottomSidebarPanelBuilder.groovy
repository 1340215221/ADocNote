package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import java.awt.BorderLayout

/**
 * 工作窗口-底边栏
 */
@WorkSingleton(builder_name)
class BottomSidebarPanelBuilder implements ISingletonBuilder {

    static final String builder_name = 'bottom_sidebar'
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

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    static String id() {
        'bottom_sidebar'
    }

}
