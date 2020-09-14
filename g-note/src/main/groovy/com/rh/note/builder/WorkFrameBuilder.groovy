package com.rh.note.builder

import com.rh.note.util.ISwingBuilder

import javax.swing.WindowConstants

/**
 * 工作窗口
 */
class WorkFrameBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.frame(id: id(),
                title: 'adoc笔记',
                pack: true,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            children()
            windowCentered()
        }
    }

    /**
     * 窗口居中
     * 设置窗口居中. 需要放在添加完子控件, pack通过子控件计算完窗口大小后
     */
    private void windowCentered() {
        swingBuilder."${id()}".locationRelativeTo = null
    }

    static String id() {
        return 'work_frame'
    }
}
