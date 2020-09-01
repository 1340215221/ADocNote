package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.JFrame
import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout

class TestApplication2 {

    static main(args) {
        def swingBuilder = new SwingBuilder()
        swingBuilder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                size: [900, 600],
                locationRelativeTo: null,
        ){
            swingBuilder.panel(id: 'base_pane',
                    layout: new BorderLayout(),
            ) {
                swingBuilder.panel(id: 'n',
                        constraints: BorderLayout.NORTH,
                        background: Color.red,
                        preferredSize: [0, 70],
                        layout: new FlowLayout(FlowLayout.LEFT, 0, 0),
                ) {
                    swingBuilder.button(text: 'java基础')
                    swingBuilder.button(text: 'java类型')
                    swingBuilder.button(text: 'integer')
                }
                swingBuilder.panel(id: 's',
                        constraints: BorderLayout.CENTER,
                        background: Color.yellow,
                )
            }
        }

        swingBuilder.frame.visible = true
    }

}
