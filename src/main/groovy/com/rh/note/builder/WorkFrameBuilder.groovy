package com.rh.note.builder


import com.rh.note.util.SwingComponent

import javax.swing.WindowConstants

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
                bounds: this.getBounds(),
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            children()
        }
    }

    static String getId() {
        return 'work_frame'
    }

    /**
     * 获得位置
     */
    private List<Integer> getBounds() {
        int w = 900
        int h = 600
        int x = (screenWidth - w) / 2
        int y = (screenHeight - h) / 2
        [x, y, w, h]
    }
}
