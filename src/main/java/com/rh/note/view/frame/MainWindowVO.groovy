package com.rh.note.view.frame

import com.rh.note.view.bound.BoundFactory
import com.rh.note.utils.Builder

import javax.swing.*

/**
 * 主窗口
 */
class MainWindowVO implements Builder {

    private BasePanelVO panel = new BasePanelVO()

    @Override
    void init() {
        swingBuilder.frame(title: 'Adoc笔记本',
                bounds: BoundFactory.getMainFrameBound(),
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            panel.init()
        }
    }

    @Override
    String getId() {
        return 'mainWindow'
    }
}
