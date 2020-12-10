package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonBuilder
import com.rh.note.event.TabbedPaneEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import javax.swing.JTabbedPane
import java.awt.BorderLayout
import java.awt.FlowLayout

/**
 * 工作窗口-编辑区面板
 */
@WorkSingleton(builder_name)
class TabbedPaneBuilder implements ISingletonBuilder {

    static final String builder_name = "tabbed_pane"
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TabbedPaneEvent event

    @Override
    void init(Closure children) {
        def tabbedPane = {
            swingBuilder.tabbedPane(id: id(),
                    constraints: BorderLayout.CENTER,
                    tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,
                    stateChanged: {
                        event.load_title_navigate_on_selected_tab()
                    }
            ){
                children()
            }
        }

        def navigate = {
            swingBuilder.panel(id: navigateId(),
                    constraints: BorderLayout.NORTH,
                    layout: new FlowLayout(FlowLayout.LEFT, 0, 0),
                    preferredSize: [0, 30],
            ) {
            }
        }

        swingBuilder.panel(constraints: BorderLayout.CENTER,
                layout: new BorderLayout(),
        ) {
            navigate()
            tabbedPane()
        }
    }

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
        swingBuilder.variables.remove(navigateId())
    }

    static String id() {
        'tabbed_pane'
    }

    static String navigateId() {
        'tabbed_pane_navigate'
    }
}