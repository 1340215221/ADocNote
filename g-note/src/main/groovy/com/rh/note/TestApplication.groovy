package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.JFrame
import java.awt.BorderLayout
import java.awt.event.KeyEvent

class TestApplication {
    public static void main(String[] args) {
        def builder = new SwingBuilder()
        builder.frame(id: 'frame',
                size: [900, 500],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ){
            builder.panel(layout: new BorderLayout()){
                builder.textPane(keyReleased: { KeyEvent event ->
                    println event.getKeyCode()
                    println event.getModifiers()
                    println event.ID
                }){
                }
            }
        }
        builder.frame.visible = true
    }
}
