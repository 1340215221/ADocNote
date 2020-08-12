package com.rh.note.view

import com.rh.note.event.TextAreaEvent
import com.rh.note.model.file.AdocFile
import com.rh.note.util.SwingComponent

import java.awt.*

/**
 * 文本区域
 */
class TextArea implements SwingComponent {

    private String filePath

    TextArea(String filePath) {
        this.filePath = filePath
    }

    @Override
    void init(Closure children) {
        def text_area = {
            swingBuilder.textArea(id: id(filePath),
                    font: new Font(null, 0, 17),
                    keyPressed: {
                        TextAreaEvent.generateIncludeBlock(it)
                        TextAreaEvent.rename(it)
                    },
                    adocFile: new AdocFile(path: filePath),
            )
        }

        def center_panel = {
            swingBuilder.panel(id: centerPanelId(filePath),
                    constraints: BorderLayout.CENTER,
                    layout: new BorderLayout(),
            ) {
                text_area()
            }
        }

        def west_panel = {
            swingBuilder.panel(id: westPanelId(filePath),
                    constraints: BorderLayout.WEST,
            )
        }

        def south_panel = {
            swingBuilder.panel(id: southPanelId(filePath),
                    constraints: BorderLayout.SOUTH,
            ) {}
        }

        def base_panel = {
            swingBuilder.panel(id: basePanelId(filePath),
                    layout: new BorderLayout(),
            ) {
                west_panel()
                center_panel()
                south_panel()
            }
        }

        def pane = swingBuilder.scrollPane(id: scrollId(filePath),
        ) {
            base_panel()
        }
        println ''
    }

    static String id(String filePath) {
        "text_area_${filePath}"
    }

    static String scrollId(String filePath) {
        "text_area_scroll_${filePath}"
    }

    static String basePanelId(String filePath) {
        "text_area_base_panel_${filePath}"
    }

    static String westPanelId(String filePath) {
        "text_area_west_${filePath}"
    }

    static String centerPanelId(String filePath) {
        "text_area_center_${filePath}"
    }

    static String southPanelId(String filePath) {
        "text_area_south_${filePath}"
    }
}
