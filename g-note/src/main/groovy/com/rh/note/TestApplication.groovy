package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.ImageIcon
import javax.swing.JFrame
import java.awt.BorderLayout

class TestApplication {
    static def builder = new SwingBuilder()
    static main(args) {
        def icon = new ImageIcon("timg.gif")
        println icon.iconHeight
        println icon.iconWidth

        builder.frame(id: 'frame',
                locationRelativeTo: null,
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                pack: true
        ){
            builder.panel(layout: new BorderLayout(),
                    preferredSize: [712, 402],
            ){
                builder.label(icon: icon)
            }
        }

        builder.frame.visible = true
    }
}
