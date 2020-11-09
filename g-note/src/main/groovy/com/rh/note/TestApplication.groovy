package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.JFrame
import java.awt.BorderLayout
import java.awt.event.KeyEvent

class TestApplication {
    public static void main(String[] args) {
        def swingBuilder = new SwingBuilder()
        swingBuilder.frame(id: 'frame',
                size: [900, 500],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ) {
            swingBuilder.panel(layout: new BorderLayout()){
                swingBuilder.textPane(keyPressed: {KeyEvent event ->
                    println event.keyCode
                    println event.modifiers
                })
            }
        }

        swingBuilder.frame.visible = true
    }
}
