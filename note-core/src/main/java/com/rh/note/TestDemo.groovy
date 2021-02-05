package com.rh.note

import groovy.swing.SwingBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.swing.*
import javax.swing.text.Element
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import java.awt.*

class TestDemo {

    static Logger log = LoggerFactory.getLogger(TestDemo)

    static main(args) {
        def builder = new SwingBuilder()
        builder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                size: [900, 500],
        ) {
            builder.panel(layout: new BorderLayout(),
            ) {
                builder.textPane(id: 'textPane',
                        font: new Font('Consolas', 0, 17),
                        text: '1\nadsfasf'
                )
            }
        }

        def textPane = builder.textPane as JTextPane
        def set = new SimpleAttributeSet()
        StyleConstants.setForeground(set, Color.decode('#CC7832'))
        textPane.getStyledDocument().setCharacterAttributes(0, 1, set, false)

        builder.frame.visible = true

        def rootElement = textPane.getDocument().getDefaultRootElement()
        println rootElement.getStartOffset()
        println rootElement.getEndOffset()
    }
}
