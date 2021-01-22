package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.WindowConstants

/**
 * 工作窗口 构建者
 */
@ComponentBean(FrameCategoryEnum.WORK)
class WorkFrameBuilder {

    public static final String id = ''
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private WorkFrameEvent event

    @PostConstruct
    void init() {
        swingBuilder.frame(id: id,
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
        }
    }
}
