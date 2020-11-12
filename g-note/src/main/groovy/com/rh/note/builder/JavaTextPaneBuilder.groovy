package com.rh.note.builder

import com.rh.note.base.ISwingBuilder
import com.rh.note.component.JavaTextPane
import com.rh.note.event.JTextPaneEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.swing.text.DefaultEditorKit
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
    /**
     * 来源编辑区文件路径
     */
    private String sourceFilePath
    /**
     * include java中的文件路径
     */
    private String includeFilePath

    /**
     * 缺少 选择行 回显
     */
    JavaTextPaneBuilder(String absolutePath, String sourceFilePath, String includeFilePath) {
        this.absolutePath = absolutePath
        this.sourceFilePath = sourceFilePath
        this.includeFilePath = includeFilePath
    }

    void init() {
        def textPane = {
            swingBuilder.jTextPane(id: id(absolutePath),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    absolutePath: absolutePath,
                    keyPressed: {
                        JTextPaneEvent.markLine(it)
                    },
                    sourceFilePath: sourceFilePath,
                    includeFilePath: includeFilePath,
            ){
                setReadOnly()
            }
        }

        swingBuilder.jScrollPane(id: scrollId(absolutePath),
                absolutePath: absolutePath,
        ){
            textPane()
        }
    }

    /**
     * 编辑相关action key
     */
    private static final List<String> list = Arrays.asList(
            DefaultEditorKit.insertContentAction,
            DefaultEditorKit.deletePrevCharAction,
            DefaultEditorKit.deleteNextCharAction,
            DefaultEditorKit.deletePrevWordAction,
            DefaultEditorKit.deleteNextWordAction,
            DefaultEditorKit.cutAction,
            DefaultEditorKit.pasteAction,
            DefaultEditorKit.insertBreakAction,
            DefaultEditorKit.defaultKeyTypedAction,
            DefaultEditorKit.insertTabAction,
            DefaultEditorKit.writableAction,
    )

    /**
     * 清除编辑功能
     */
    private void setReadOnly() {
        def textPane = swingBuilder."${id(absolutePath)}" as JavaTextPane
        // 清除 textPane.actionMap.parent.parent 中的编辑action
        list.each {
            textPane.actionMap.parent.parent.remove(it)
        }
        def parent = textPane.actionMap.parent
        // 清除 textPane.actionMap.parent.keymap.parent 中的编辑action
        def field = parent.class.getDeclaredField('keymap')
        field.setAccessible(true)
        def keymap = field.get(parent)
        def pParent = keymap.class.getDeclaredField('parent')
        pParent.setAccessible(true)
        pParent.set(keymap, null)
    }

    static String id(String absolutePath) {
        return "java_text_pane_${absolutePath}"
    }

    static String scrollId(String absolutePath) {
        return "java_text_pane_scroll_${absolutePath}"
    }
}
