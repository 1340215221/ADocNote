package com.rh.note

import cn.hutool.core.io.FileUtil
import cn.hutool.core.util.CharsetUtil
import cn.hutool.core.util.ReflectUtil
import groovy.swing.SwingBuilder

import javax.swing.JFrame
import javax.swing.JTextPane
import javax.swing.text.AbstractDocument
import javax.swing.text.AttributeSet
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.Style
import javax.swing.text.StyleConstants
import java.awt.BorderLayout
import java.awt.Font
import java.awt.event.KeyEvent
import java.lang.reflect.Method

class TestDemo {

    static main(args) {
        /*
        def document = new DefaultStyledDocument()
        AbstractDocument.BranchElement set = document.getRootElements()[0].getElement(0).getAttributes()
        def method = AbstractDocument.getDeclaredMethod('getAttributeContext')
        method.setAccessible(true)
        def context = method.invoke(document)
        def attributes = ReflectUtil.getFieldValue(set, 'attributes')
        attributes = context.addAttribute(attributes, StyleConstants.LineSpacing, 0.5f)
        ReflectUtil.setFieldValue(set, 'attributes', attributes)
*/

        def swingBuilder = new SwingBuilder()

        swingBuilder.frame(id: 'frame',
                pack: true,
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ){
            swingBuilder.panel(preferredSize: [900, 500],
                    layout: new BorderLayout(),
            ){
                swingBuilder.scrollPane() {
                    swingBuilder.textPane(id: 'textPane',
//                            styledDocument: document,
                            keyReleased: { KeyEvent event ->
                                println "__${event.keyChar}__"
                            },
                            font: new Font('Ubuntu Mono', Font.PLAIN, 17),
                    ) {
                        def pane = swingBuilder.textPane
                        println pane.name
                    }
                }
            }
        }


        def textPane = swingBuilder.textPane as JTextPane
//        StyleConstants.setLineSpacing(set, 0.9f)

        swingBuilder.frame.locationRelativeTo = null
        swingBuilder.frame.visible = true

        Style style=TestDemo2.style
        StyleConstants.setLineSpacing(style, 0.4f)
        StyleConstants.setFontSize(style, 17)
        textPane.setLogicalStyle(style)

//        def set = new SimpleAttributeSet()
//        StyleConstants.setLineSpacing(set, 0.9f)
//        textPane.getStyledDocument().setCharacterAttributes(0, 1000, set, true)
//

//        textPane.setText('Returns an ImageIcon, or null if the path was invalid.\nadfsafaaaaaaaaaaaaaaaaaaaaaaaaaafaf')
        textPane.read(FileUtil.getReader(new File('111'), CharsetUtil.UTF_8), null)


//
//
    }
}
