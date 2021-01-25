package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import java.awt.BorderLayout

/**
 * 左边栏菜单面板
 */
@ComponentBean(FrameCategoryEnum.WORK)
class LeftSidebarMenuPanelBuilder {

    @Autowired
    private SwingBuilder swingBuilder

    @PostConstruct
    void init() {
        swingBuilder.panel(constraints: BorderLayout.CENTER,
                layout: new BorderLayout(),
        ){
        }
    }

}
