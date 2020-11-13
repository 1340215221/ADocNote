package com.rh.note.builder

import com.rh.note.base.ISwingBuilder
import com.rh.note.component.JavaTextPane
import com.rh.note.event.JTextPaneEvent

import javax.swing.text.DefaultEditorKit
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
    /**
     * 来源编辑区文件路径
     */
    private String sourceFilePath
    /**
     * include java中的文件路径
     */
    private String includeFilePath
    /**
     * 标记行 1
     */
    private Integer markLineNumber1
    /**
     * 标记行 2
     */
    private Integer markLineNumber2

    /**
     * 缺少 选择行 回显
     */
    JavaTextPaneBuilder(String absolutePath, String sourceFilePath, String includeFilePath, Integer markLineNumber1, Integer markLineNumber2) {
        this.absolutePath = absolutePath
        this.sourceFilePath = sourceFilePath
        this.includeFilePath = includeFilePath
        this.markLineNumber1 = markLineNumber1
        this.markLineNumber2 = markLineNumber2
    }

    void init() {
        def textPane = {
            swingBuilder.jTextPane(id: id(absolutePath),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    absolutePath: absolutePath,
                    sourceFilePath: sourceFilePath,
                    includeFilePath: includeFilePath,
                    markLineNumber1: markLineNumber1,
                    markLineNumber2: markLineNumber2,
                    keyPressed: {
                        JTextPaneEvent.markLine(it)
                    },
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
        // 创建默认keymap
        def clazz = Class.forName('javax.swing.text.JTextComponent$DefaultKeymap')
        def instance = clazz.newInstance("BasicTextPaneUI", null)
        // 修改textPane默认keymap
        field.set(parent, instance)
    }

    static String id(String absolutePath) {
        return "java_text_pane_${absolutePath}"
    }

    static String scrollId(String absolutePath) {
        return "java_text_pane_scroll_${absolutePath}"
    }
}
