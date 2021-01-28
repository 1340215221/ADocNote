package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.event.TabbedPaneEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.JTabbedPane
import java.awt.BorderLayout

/**
 * 编辑区面板
 */
@ComponentBean(FrameCategoryEnum.WORK)
class TabbedPaneBuilder implements BaseBuilder {

    public static final String id = 'text_tabbed_pane'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TabbedPaneEvent event

    @PostConstruct
    void init() {
        swingBuilder.tabbedPane(id: id,
                constraints: BorderLayout.CENTER,
                tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,
                stateChanged: {
//                    event.load_title_navigate_on_selected_tab()
                }
        ){
        }
    }

    JTabbedPane getTabbedPane() {
        return swingBuilder."${id}"
    }
}
