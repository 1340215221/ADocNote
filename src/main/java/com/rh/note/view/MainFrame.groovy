package com.rh.note.view

import groovy.swing.SwingBuilder

import javax.swing.*

class MainFrame extends JFrame {

    void init() {
        def swingBuilder = new SwingBuilder()

        def text = {
            swingBuilder.textArea(id: 'text',
                    rows: 20,
                    columns: 50,
            )
        }

        def scrollPane = {
            swingBuilder.scrollPane(id: 'scrollPane') {
                text()
            }
        }

        def panel = {
            swingBuilder.panel(id: 'panel'){
                scrollPane()
            }
        }

        def frame = swingBuilder.frame(id: 'frame',
                title: '笔记app',
                bounds: [0, 0, 800, 600],
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ) {
            panel()
        }

        frame.visible = true


        def p = swingBuilder.panel
        println p
    }

}
