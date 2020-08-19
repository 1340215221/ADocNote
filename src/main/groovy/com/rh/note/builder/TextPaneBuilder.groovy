package com.rh.note.builder

import com.rh.note.util.SwingComponent

import javax.swing.text.DefaultStyledDocument

/**
 * 编辑区
 */
class TextPaneBuilder implements SwingComponent {

    private String filePath

    TextPaneBuilder(String filePath) {
        this.filePath = filePath
    }

    void init() {
        init{}
    }

    @Override
    void init(Closure children) {
        def textPane = {
            swingBuilder.textPane(id: id(filePath),
                    styledDocument: new DefaultStyledDocument(),
            )
        }

        swingBuilder.scrollPane(id: scrollId(filePath)){
            textPane()
        }
    }

    static String id(String filePath) {
        return "text_pane_${filePath}"
    }

    static String scrollId(String filePath) {
        return "text_pane_scroll_${filePath}"
    }
}
