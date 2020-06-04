package com.rh.note.view

import com.rh.note.model.MainViewBoundEnum

import javax.swing.*

class MainFrame implements SwingBuilderImpl {

    @Override
    init(Closure runnable) {
        swingBuilder.frame(id: id,
                title: 'adoc笔记',
                bounds: MainViewBoundEnum.main_frame.bound,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            runnable()
        }
    }

    @Override
    final String getId() {
        return 'main_frame'
    }

}
