package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.JFrame
import javax.swing.JScrollBar
import javax.swing.JScrollPane
import javax.swing.JTextPane
import java.awt.BorderLayout

class TestApplication {
    public static void main(String[] args) {
        def swingBuilder = new SwingBuilder()
        swingBuilder.frame(id: 'frame',
                size: [500, 500],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
                locationRelativeTo: null,
        ){
            swingBuilder.panel(id: 'panel',
                    layout: new BorderLayout(),
            ) {
                swingBuilder.scrollPane(id: 'scroll'){
                    swingBuilder.textPane(id: 'text',
                            text:'\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n1\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n'
                    ){
                    }
                }
            }
        }
        swingBuilder.frame.visible = true

        def text = swingBuilder.text as JTextPane
        println text.getDocument().getLength()
        //34/59

        def scroll = swingBuilder.scroll as JScrollPane
        def bar = scroll.getVerticalScrollBar()

        // 编辑区, 总行数, 要定位的行数
        // 滚动, 最大值 高度
        int d = (int) (34d * bar.getMaximum() / 59d - bar.height * 4 / 10)
        println d

        bar.setValue(d)
    }
}
