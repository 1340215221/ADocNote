package com.rh.note.builder


import com.rh.note.event.TabbedPaneEvent
import com.rh.note.util.ISwingBuilder

import javax.swing.JTabbedPane
import java.awt.BorderLayout
import java.awt.FlowLayout

/**
 * 工作窗口-编辑区面板
 */
class TabbedPaneBuilder implements ISwingBuilder {
    void init(Closure children) {
        def tabbedPane = {
            swingBuilder.tabbedPane(id: id(),
                    constraints: BorderLayout.CENTER,
                    tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,
                    stateChanged: {
                        TabbedPaneEvent.load_title_navigate_on_selected_tab()
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

    static String id() {
        'tabbed_pane'
    }

    static String navigateId() {
        'tabbed_pane_navigate'
    }
}
