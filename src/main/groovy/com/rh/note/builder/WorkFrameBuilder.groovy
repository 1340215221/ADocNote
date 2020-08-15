package com.rh.note.builder

import com.rh.note.util.SwingComponent

import javax.swing.*

/**
 * 主窗口
 */
class WorkFrameBuilder implements SwingComponent {

    /**
     * 屏幕宽
     */
    private static final int screenWidth = 1920
    /**
     * 屏幕高
     */
    private static final int screenHeight = 1080

    @Override
    void init(Closure children) {
        swingBuilder.frame(id: id,
                title: 'adoc笔记',
                size: [900, 600],
                locationRelativeTo: null,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            children()
        }
    }

    static String getId() {
        return 'work_frame'
    }
}
