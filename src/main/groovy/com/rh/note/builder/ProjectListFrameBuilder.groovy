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
                size: [800, 500],
                locationRelativeTo: null,
                resizable: false,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            children()
        }
    }

    static String getId() {
        'project_list_frame'
    }
}
