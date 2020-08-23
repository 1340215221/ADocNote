package com.rh.note.builder

import com.rh.note.util.SwingComponent

import javax.swing.JTabbedPane
import java.awt.*

/**
 * 编辑区域
 */
class EditAreaBuilder implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.tabbedPane(id: id,
//                background: Color.cyan,
                constraints: BorderLayout.CENTER,
                tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,
        ){
            children()
        }
    }

    static String getId() {
        'edit_area'
    }
}
