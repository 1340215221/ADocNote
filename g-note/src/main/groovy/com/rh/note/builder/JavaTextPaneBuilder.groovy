package com.rh.note.builder

import com.rh.note.base.ISwingBuilder

import javax.swing.text.DefaultStyledDocument
import java.awt.Font

/**
 * java文件控件
 */
class JavaTextPaneBuilder implements ISwingBuilder {

    /**
     * 文件绝对路径
     */
    private String absolutePath

    JavaTextPaneBuilder(String absolutePath) {
        this.absolutePath = absolutePath
    }

    void init() {
        def textPane = {
            swingBuilder.jTextPane(id: id(absolutePath),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    editable: false,
                    absolutePath: absolutePath,
            ){
            }
        }

        swingBuilder.jScrollPane(id: scrollId(absolutePath),
                absolutePath: absolutePath,
        ){
            textPane()
        }
    }

    static String id(String absolutePath) {
        return "java_text_pane_${absolutePath}"
    }

    static String scrollId(String absolutePath) {
        return "java_text_pane_scroll_${absolutePath}"
    }
}
