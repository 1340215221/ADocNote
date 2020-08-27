package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.*
import javax.swing.text.JTextComponent
import javax.swing.text.Keymap
import javax.swing.text.TextAction
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.util.concurrent.ConcurrentHashMap

class TestApplication {

    public static void main(String[] args) {
        def builder = new SwingBuilder()
        builder.frame(id: 'frame',
                pack: true,
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ){
            builder.panel(preferredSize: [900,500],
                    layout: new BorderLayout(),
            ) {
                builder.textPane(id: 'textPane',
                        text: 'hello\nworld\napplication time\ncreate id',
                )
            }
        }

        def pane = builder.textPane as JTextPane
        def stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0)

        def parent = pane.getKeymap()
        def newKeymap = JTextComponent.addKeymap("newKeymap", parent)
        newKeymap.addActionForKeyStroke(stroke, new TextAction("my_action") {
            @Override
            void actionPerformed(ActionEvent e) {
                println 'actionPerformed'
            }
        })
        pane.setKeymap(newKeymap)

        builder.frame.locationRelativeTo = null
        builder.frame.visible = true
    }

}
