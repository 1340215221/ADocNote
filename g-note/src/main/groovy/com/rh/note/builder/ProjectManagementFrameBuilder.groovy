package com.rh.note.builder

import com.rh.note.util.ISwingBuilder

import javax.swing.WindowConstants

/**
 * 项目管理窗口
 */
class ProjectManagementFrameBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.frame(id: id(),
                pack: true,
                resizable: false,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
                title: '打开项目',
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
        'project_list_frame'
    }
}
