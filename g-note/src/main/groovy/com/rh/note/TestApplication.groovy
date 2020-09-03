package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.*
import java.awt.*
import java.awt.event.MouseEvent

class TestApplication {
    public static void main(String[] args) {
        def swingBuilder = new SwingBuilder()
        swingBuilder.frame(id: 'frame',
                size: [900, 500],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ) {
            swingBuilder.panel(layout:  new BorderLayout()) {
                swingBuilder.textPane(id: 'textPane',
                        text: 'afafs\nasfasdf\nafasfasf',
                        mouseClicked: {MouseEvent event ->
                            if (event.modifiers == 18) {
                                println 'mouseClicked'
                            }
                        }
                )
            }
        }.visible = true
    }
}
