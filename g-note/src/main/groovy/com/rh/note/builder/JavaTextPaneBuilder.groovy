package com.rh.note.builder

import com.rh.note.base.ISwingBuilder
import com.rh.note.component.JavaTextPane
import com.rh.note.event.JTextPaneEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.swing.text.DefaultStyledDocument
import java.awt.Font

/**
 * java文件控件
 */
class JavaTextPaneBuilder implements ISwingBuilder {

    private static final Logger log = LoggerFactory.getLogger(JavaTextPaneBuilder)
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
                    keyPressed: {
                        JTextPaneEvent.markLine(it)
                    }
            ){
                showCaret()
            }
        }

        swingBuilder.jScrollPane(id: scrollId(absolutePath),
                absolutePath: absolutePath,
        ){
            textPane()
        }
    }

    /**
     * 初始化显示光标
     */
    private void showCaret() {
        try {
            def textPane = swingBuilder."${id(absolutePath)}" as JavaTextPane
            def caretVisible = textPane.caret.visible
            if (!caretVisible) {
                textPane.caret.dot = 0
                textPane.caret.visible = true
            }
        } catch (Exception e) {
            log.error("[java文件编辑区-光标显示 失败] absolutePath={}", absolutePath, e)
        }
    }

    static String id(String absolutePath) {
        return "java_text_pane_${absolutePath}"
    }

    static String scrollId(String absolutePath) {
        return "java_text_pane_scroll_${absolutePath}"
    }
}
