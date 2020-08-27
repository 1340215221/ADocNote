package com.rh.note.builder

import com.rh.note.event.TextAreaEvent
import com.rh.note.util.SwingComponent

import javax.swing.text.DefaultStyledDocument
import java.awt.*

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
                    name: id(filePath),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    keyPressed: {
                        TextAreaEvent.generateIncludeBlock(it)
                        TextAreaEvent.rename(it)
                    },
                    filePath: filePath,
            )
        }

        swingBuilder.scrollPane(id: scrollId(filePath),
                name: scrollId(filePath),
        ){
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
