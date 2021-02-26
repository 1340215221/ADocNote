package com.rh.note


import groovy.swing.SwingBuilder

import javax.swing.*
import java.awt.*

class TestDemo extends JPanel {

    static {
//        FlatDarculaLaf.install()
    }
    static def builder = new SwingBuilder()

    static main(args) {
        builder.frame(id: 'frame',
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ) {
            /**
            builder.panel(id: 'panel',
                    layout: new BorderLayout(),
                    preferredSize: [900, 500],
            ) {
                builder.scrollPane(id: 'scrollPane',
//                        background: Color.red,
                ){
                    builder.textPane(id: 'textPane',
                            text: '1111',
                            opaque: false,
                    )
                    def pane = builder.scrollPane as JScrollPane
//                    pane.viewport.background = Color.red
//                    pane.viewport.opaque = false
                }
            }
             */
        }

        def frame = builder.frame as JFrame
        def demo = new TestDemo()
        demo.setLayout(new BorderLayout())
        demo.setBackground(Color.green)
        demo.setPreferredSize(new Dimension(900, 500))
        frame.add(demo)
        frame.pack()

        builder.frame.locationRelativeTo = null
        builder.frame.visible = true
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g)
        g.setColor(Color.RED)//设置画图的颜色
        g.fillRect(200, 200, 10000, 10000);
    }
}
