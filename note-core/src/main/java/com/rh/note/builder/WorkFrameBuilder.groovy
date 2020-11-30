package com.rh.note.builder

import com.rh.note.base.ISwingBuilder
import com.rh.note.event.WorkFrameEvent

import javax.swing.WindowConstants

/**
 * 工作窗口
 */
class WorkFrameBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.frame(id: id(),
                title: 'adoc笔记',
                pack: true,
                defaultCloseOperation: WindowConstants.DO_NOTHING_ON_CLOSE,
                windowClosing: {
                    // 主线程不能阻塞, 阻塞时无法渲染页面
                    swingBuilder.doOutside {
                        WorkFrameEvent.save_all_text_pane()
                        WorkFrameEvent.close_frame()
                    }
                }
        ){
            children()
        }
        windowCentered()
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
