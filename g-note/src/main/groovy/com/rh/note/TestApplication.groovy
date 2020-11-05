package com.rh.note


import groovy.swing.SwingBuilder
import org.apache.commons.lang3.StringUtils

import javax.swing.JFrame
import javax.swing.JPopupMenu
import javax.swing.JTextPane
import javax.swing.event.MenuKeyEvent
import javax.swing.text.DefaultEditorKit
import javax.swing.text.JTextComponent
import java.awt.Point
import java.awt.event.ActionEvent

class TestApplication {
    public static void main(String[] args) {
        def swingBuilder = new SwingBuilder()
        swingBuilder.frame(id: 'frame',
                size: [900, 600],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ) {
            swingBuilder.panel(preferredSize: [500, 500]
            ) {
                swingBuilder.textPane(id: 'textPane',
                        preferredSize: [500, 500],
                        keyTyped: {
//                            println 'textPane keyTyped'
                        },
                        keyPressed: {
//                            println 'textPane keyPressed'
                        },
                        keyReleased: {
//                            println 'keyReleased'
                            def textPane = it.source as JTextPane
                            def position = it.source.caret.magicCaretPosition as Point
                            if (!position) {
                                return
                            }
                            swingBuilder.doOutside {
                                def tp = textPane
                                def p = position
                                def menu = swingBuilder.menu as JPopupMenu
                                menu.show(tp, (int) p.x - 7, (int) p.y + 15)
                            }
                        },
                ) {
                }

                swingBuilder.popupMenu(id: 'menu',
                        menuKeyPressed: {
                            def event = it as MenuKeyEvent
                            def textPane = swingBuilder.textPane as JTextPane
                            def inputStr = String.valueOf(event.keyChar)
                            def actionEvent = new ActionEvent(textPane, 1001, inputStr, event.when, event.modifiers)
                            textPane.caret.visible = true
                            if (StringUtils.isNotBlank(inputStr) && inputStr.matches('[0-9a-zA-Z_\\-]')) {
                                new DefaultEditorKit.InsertContentAction().actionPerformed(actionEvent)
                            }
                        },
                        menuKeyTyped: {
                            println '2'
                        },
                        menuKeyReleased: {
                            println '3'
                        }
                ) {
                    swingBuilder.menuItem(id: 'item1',
                            text: 'name',
                    )
                    swingBuilder.menuItem(id: 'item2',
                            text: 'age',
                    )
                }
            }
        }

        def textPane1 = swingBuilder.textPane as JTextPane
        def newKeymap = JTextComponent.addKeymap("textPane", textPane1.keymap)
        textPane1.setKeymap(newKeymap)

        swingBuilder.frame.visible = true
    }
}
