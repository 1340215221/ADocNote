package com.rh.note

import com.formdev.flatlaf.FlatDarculaLaf
import com.rh.note.api.GitApi
import groovy.swing.SwingBuilder

import javax.swing.*
import java.awt.*

class TestDemo {

    static {
        FlatDarculaLaf.install()
    }
    static def builder = new SwingBuilder()

    static main(args) {
        /**
        builder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                pack: true,
        ){
            builder.panel(layout: new BorderLayout()){
                builder.button(text: '同步',
                        mouseClicked: {
                            builder.dialog(id: 'dialog',
                                    pack: true,
                            ){
                                builder.label(text: '同步项目中',
                                        constraints: BorderLayout.NORTH,
                                )
                                builder.progressBar(id: 'progressBar',
                                        preferredSize: [500, 20],
                                )
                            }
                            builder.dialog.locationRelativeTo = null
                            builder.dialog.visible = true

                            Thread.start {
                                int i = 0
                                while (i < 100) {
                                    builder.progressBar.value = i
                                    i++
                                    Thread.sleep(500)
                                }
                            }
                        }
                )
            }
        }

        builder.frame.locationRelativeTo = null
        builder.frame.visible = true
         */

        pull()
    }

    static void pull() {
        def git = new GitApi()
        git.pull('/my_code/test-jgit')
    }
}
