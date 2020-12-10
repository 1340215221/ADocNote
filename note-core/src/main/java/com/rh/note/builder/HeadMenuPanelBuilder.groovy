package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import java.awt.BorderLayout

/**
 * 工作窗口-头部菜单栏
 */
@WorkSingleton(builder_name)
class HeadMenuPanelBuilder implements ISingletonBuilder {

    static final String builder_name = 'head_menu'
    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.panel(id: id(),
                constraints: BorderLayout.NORTH,
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
        'head_menu'
    }
}
