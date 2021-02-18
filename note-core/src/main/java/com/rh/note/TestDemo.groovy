package com.rh.note


import groovy.swing.SwingBuilder

import javax.swing.*
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import java.awt.BorderLayout

class TestDemo {

    static main(args) {
        def builder = new SwingBuilder()
        builder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                size: [900, 500],
                locationRelativeTo: null,
        ){
            builder.panel(layout: new BorderLayout()){
                builder.scrollPane(){
                    builder.textPane(id: 'textPane'){
                    }
                }
            }
        }

        builder.frame.visible = true

        def textPane = builder.textPane as JTextPane
        def set = new SimpleAttributeSet()
        StyleConstants.setSpaceBelow(set, 2)
//        StyleConstants.setSpaceAbove(set, 20)
        textPane.getStyledDocument().setParagraphAttributes(0, 1, set, true)
    }
}
