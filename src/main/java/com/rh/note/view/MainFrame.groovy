package com.rh.note.view

import com.rh.note.factory.MainFrameFactory
import com.rh.note.utils.IInit
import groovy.swing.SwingBuilder
import groovy.swing.factory.ComponentFactory

import javax.swing.*

/**
 * 主窗口
 */
class MainFrame extends JFrame implements IInit {

    void init() {
        def swingBuilder = new SwingBuilder()

        swingBuilder.registerFactory('mainFrame', new MainFrameFactory())
        swingBuilder.registerFactory('textPanel', new ComponentFactory(TextPanel))

        def textPanel = {
            swingBuilder.textPanel()
        }

        def frame = swingBuilder.mainFrame(title: 'Adoc笔记本',
                bounds: [0, 0, 500, 300],
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE
        ) {
            textPanel()
        }

        frame.visible = true
    }

}
