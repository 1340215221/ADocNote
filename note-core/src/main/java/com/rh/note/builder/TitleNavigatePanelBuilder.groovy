package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import java.awt.BorderLayout
import java.awt.FlowLayout

/**
 * 标题导航面板
 */
@ComponentBean(FrameCategoryEnum.WORK)
class TitleNavigatePanelBuilder {

    public static final String id = 'title_navigate_panel'
    @Autowired
    private SwingBuilder swingBuilder

    @PostConstruct
    void init() {
        swingBuilder.panel(id: id,
                constraints: BorderLayout.NORTH,
                layout: new FlowLayout(FlowLayout.LEFT, 0, 0),
                preferredSize: [0, 30],
        ){
        }
    }

}
