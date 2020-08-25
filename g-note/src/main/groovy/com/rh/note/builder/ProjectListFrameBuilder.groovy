package com.rh.note.builder

import com.rh.note.util.SwingComponent

import javax.swing.*

/**
 * 项目列表
 */
class ProjectListFrameBuilder implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.frame(id: id,
                pack: true,
                resizable: false,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
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
        swingBuilder."${id}".locationRelativeTo = null
    }

    static String getId() {
        'project_list_frame'
    }
}
