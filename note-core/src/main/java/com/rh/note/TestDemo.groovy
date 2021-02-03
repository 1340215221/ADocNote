package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.*
import javax.swing.text.MutableAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument
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
                        text: '11111111111111111'
                ) {
                }
            }
        }

        builder.frame.visible = true

        def textPane = builder.textPane as JTextPane
        def document = textPane.getStyledDocument()
        def rootElement = document.getDefaultRootElement()
        MutableAttributeSet attributes = (MutableAttributeSet) rootElement.getAttributes()
        StyleConstants.setLineSpacing(attributes, 0.9f)
    }
}
