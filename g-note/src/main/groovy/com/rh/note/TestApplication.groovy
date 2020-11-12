package com.rh.note


import groovy.swing.SwingBuilder

import javax.swing.JFrame
import javax.swing.JTextPane
import javax.swing.text.DefaultEditorKit
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument
import java.awt.BorderLayout
import java.awt.Color
import java.lang.reflect.Field

class TestApplication {
    public static void main(String[] args) {
        def builder = new SwingBuilder()
        builder.frame(id: 'frame',
                size: [900, 500],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ) {
            builder.panel(layout: new BorderLayout()) {
                builder.tabbedPane() {
                    builder.textPane(id: 'textPane1',
                            text: 'afdafa\nafdasf\nafasfsa',
                    ) {
                    }
                    builder.textPane(id: 'textPane2',
                            text: '11211\n13423\n23t56423',
                    ) {
                    }
                }
            }
        }
        builder.frame.visible = true

        def textPane = builder.textPane1 as JTextPane
        def textPane2 = builder.textPane2 as JTextPane
        def rootElement = textPane.getDocument().getDefaultRootElement()
        def element = rootElement.getElement(0)

        SimpleAttributeSet aSet = new SimpleAttributeSet();
        StyleConstants.setBackground(aSet, Color.green);
        StyledDocument doc = textPane.getStyledDocument();

        doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false);



        Arrays.asList(
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
        ).each {key ->
            textPane.actionMap.parent.parent.remove(key)
            textPane2.actionMap.parent.parent.remove(key)
        }

        // 添加 回车 事件
        def parent = textPane.actionMap.parent

        def field = parent.class.getDeclaredField('keymap')
        field.setAccessible(true)
        def get = field.get(parent)
        def f = get.class.getDeclaredField('parent')
        f.setAccessible(true)
        f.set(get, null)
        println ''
    }
}
