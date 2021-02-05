package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.*
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import java.awt.*

class TestDemo {

    static main(args){
        def builder = new SwingBuilder()
        builder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                size: [900, 500],
        ) {
            builder.panel(layout: new BorderLayout(),
            ) {
                builder.textPane(id: 'textPane',
                )
            }
        }

        def textPane = builder.textPane as JTextPane

        def element = textPane.styledDocument.defaultRootElement
        def set1 = new SimpleAttributeSet(element.getElement(0).attributes)
        StyleConstants.setLineSpacing(set1, 0.2f)
        StyleConstants.setFontSize(set1, 20)
        StyleConstants.setForeground(set1, Color.decode('#7a2518'))
        textPane.styledDocument.setParagraphAttributes(element.startOffset, element.endOffset, set1, true)
        textPane.setText('111')

//        textPane.read(FileUtil.getReader('D:\\my_code\\note_refactor\\111', CharsetUtil.UTF_8), null)

        builder.frame.visible = true
    }
}
