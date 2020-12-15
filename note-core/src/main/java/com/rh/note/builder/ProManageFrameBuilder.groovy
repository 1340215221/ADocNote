package com.rh.note.builder

import com.rh.note.annotation.ProManageSingleton
import com.rh.note.common.IFrameBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import javax.swing.JFrame
import javax.swing.WindowConstants

/**
 * 项目管理窗口
 */
@ProManageSingleton(ProManageFrameBuilder.builder_name)
class ProManageFrameBuilder implements IFrameBuilder {

    static final String builder_name = "project_list_frame"
    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.frame(id: id(),
                pack: true,
                resizable: false,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
                title: '打开项目',
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

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder."${id()}".dispose()
    }

    static String id() {
        'project_list_frame'
    }

    JFrame getFrame() {
        return swingBuilder."${id()}"
    }
}
