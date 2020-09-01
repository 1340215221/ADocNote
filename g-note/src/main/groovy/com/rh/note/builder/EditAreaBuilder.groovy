package com.rh.note.builder

import com.rh.note.util.SwingComponent

import javax.swing.*
import java.awt.*

/**
 * 编辑区域
 */
class EditAreaBuilder implements SwingComponent {
    @Override
    void init(Closure children) {
        def tabbedPane = {
            swingBuilder.tabbedPane(id: id,
//                background: Color.cyan,
                    constraints: BorderLayout.CENTER,
                    tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,
            ){
                children()
            }
        }

        def navigate = {
            swingBuilder.panel(id: navigateId,
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

    static String getId() {
        'edit_area'
    }

    static String getNavigateId() {
        'edit_area_navigate'
    }
}
