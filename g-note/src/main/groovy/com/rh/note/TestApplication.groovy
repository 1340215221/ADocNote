package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.JFrame
import javax.swing.JTextPane
import javax.swing.text.DefaultEditorKit
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font

class TestApplication {
    static def builder = new SwingBuilder()
    public static void main(String[] args) {
        builder.frame(id: 'frame',
                size: [900, 500],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ){
            builder.panel(layout: new BorderLayout(),
            ){
                builder.tabbedPane(){
                    builder.textPane(id: 'tp',
                            text: '112313'
                    ){
                    }

                    builder.textPane(id: 'textPane',
                            styledDocument: new DefaultStyledDocument(),
                            text: 'asfasf\nafddasfdsa\nafa',
                            font: new Font(null, 0, 17),
                            mouseClicked: {
                                // 修改背景色
                                def textPane = builder.textPane as JTextPane
                                def rootElement = textPane.getDocument().getDefaultRootElement()
                                def element = rootElement.getElement(0)

                                SimpleAttributeSet aSet = new SimpleAttributeSet()
                                StyleConstants.setBackground(aSet, Color.green)
                                StyledDocument doc = textPane.getStyledDocument()

                                doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false)
                            },
                            keyPressed: {
                                // 修改背景色
                                def textPane = builder.textPane as JTextPane
                                def rootElement = textPane.getDocument().getDefaultRootElement()
                                def element = rootElement.getElement(0)

                                SimpleAttributeSet aSet = new SimpleAttributeSet()
                                StyleConstants.setBackground(aSet, textPane.getBackground())
                                StyledDocument doc = textPane.getStyledDocument()

                                doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false)
                            }
                    ){
                    }
                }
            }
        }

        // 删除编辑相关action
        setReadOnly()

        builder.frame.visible = true

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
    private static void setReadOnly() {
        def textPane = builder.textPane as JTextPane
        // 清除 textPane.actionMap.parent.parent 中的编辑action
        list.each {
            textPane.actionMap.parent.parent.remove(it)
        }
        def parent = textPane.actionMap.parent
        // 清除 textPane.actionMap.parent.keymap.parent 中的编辑action
        def field = parent.class.getDeclaredField('keymap')
        field.setAccessible(true)

        def clazz = Class.forName('javax.swing.text.JTextComponent$DefaultKeymap')
        def instance = clazz.newInstance("BasicTextPaneUI", null)

        field.set(parent, instance)
    }
}
