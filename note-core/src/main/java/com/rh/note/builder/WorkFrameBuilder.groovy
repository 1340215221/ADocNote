package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.DefaultBuilder
import com.rh.note.event.WorkFrameEvent
import groovy.swing.SwingBuilder
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import javax.swing.JFrame
import javax.swing.WindowConstants

/**
 * 工作窗口
 */
@WorkSingleton
class WorkFrameBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private WorkFrameEvent event

    @Override
    void init(Closure children) {
        swingBuilder.frame(id: id(),
                title: 'adoc笔记',
                pack: true,
                defaultCloseOperation: WindowConstants.DO_NOTHING_ON_CLOSE,
                windowClosing: {
                    // 主线程不能阻塞, 阻塞时无法渲染页面
                    swingBuilder.doOutside {
                        event.save_all_text_pane()
                        event.close_frame()
                    }
                }
        ) {
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

    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    static String id() {
        return 'work_frame'
    }

    @NotNull JFrame getWorkFrame() {
        return swingBuilder."${id()}"
    }
}
