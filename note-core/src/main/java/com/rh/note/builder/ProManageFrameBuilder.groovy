package com.rh.note.builder

import com.rh.note.annatation.ProjectManage
import com.rh.note.common.DefaultBuilder
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.swing.WindowConstants

/**
 * 项目管理窗口
 */
@ProjectManage
class ProManageFrameBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder

    @Override
    void init(Closure children) {
        swingBuilder.frame(id: id(),
                pack: true,
                resizable: false,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
                title: '打开项目',
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
        'project_list_frame'
    }
}
