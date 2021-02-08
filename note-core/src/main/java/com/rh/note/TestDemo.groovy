package com.rh.note

import com.rh.note.factory.AdocTextPaneFactory
import groovy.swing.SwingBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.swing.*
import java.awt.*
import java.awt.event.ContainerEvent

class TestDemo {

    static Logger log = LoggerFactory.getLogger(TestDemo)

    static main(args) {
        def builder = new SwingBuilder()
        builder.registerFactory('textPane', new AdocTextPaneFactory())

        builder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                size: [900, 500],
        ) {
            builder.panel(layout: new BorderLayout(),
            ) {
                builder.tabbedPane(id: 'tabbedPane',
                        tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT,
                        componentAdded: {ContainerEvent e ->
                            println e.child.class.name
                        }
                ) {
                    builder.textPane(id: "textPane",
                            font: new Font('Consolas', 0, 17),
                            text: '1\nadsfasf',
                            lineSpacing: 0.9f,
                    )
                }
            }
        }

        builder.frame.visible = true
    }
}
