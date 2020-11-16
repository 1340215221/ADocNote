package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.JFrame
import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI
import java.awt.BorderLayout
import java.awt.event.WindowEvent

class TestApplication {
    static def builder = new SwingBuilder()
    public static void main(String[] args) {
        def dialog = builder.dialog(id: 'dialog',
                locationRelativeTo: null,
                size: [200, 200]
        ) {
            builder.label(text: 'qwerq32').visible = true
        }

        def lock = new Object()

        builder.frame(id: 'frame',
                pack: true,
                defaultCloseOperation: JFrame.DO_NOTHING_ON_CLOSE,
                location: [200,200],
                windowClosing: { WindowEvent event ->
                    dialog.visible = true
                    builder.doOutside {
                        Thread.sleep(3000)
                        dialog.dispose()
                        builder.frame.dispose()
                    }
                }
        ){
            builder.panel(id: 'panel',
                    layout: new BorderLayout(),
                    preferredSize: [900, 500],
            ){
                builder.textPane(text: 'afdafasf',
                ){}
            }
        }

        builder.frame.visible = true

        println Thread.currentThread().name

    }
}
