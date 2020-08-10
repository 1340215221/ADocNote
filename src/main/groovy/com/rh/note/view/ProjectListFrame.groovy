package com.rh.note.view

import com.rh.note.util.SwingComponent

import javax.swing.WindowConstants

/**
 * 项目列表
 */
class ProjectListFrame implements SwingComponent {
    @Override
    void init(Closure children) {
        swingBuilder.frame(id: id,
                bounds: [500, 200, 800, 500],
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
