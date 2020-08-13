package com.rh.note.event

import com.rh.note.action.ProjectListAction
import com.rh.note.config.BeanConfig

import java.awt.event.MouseEvent

/**
 * 项目列表
 */
class ProjectListEvent {

    private static final ProjectListAction projectListAction = BeanConfig.projectListAction

    /**
     * 项目列表点击事件
     */
    static def project_list_mouse_client = { MouseEvent event ->
        if (event.clickCount > 1) {
            projectListAction.openProject()
        }
    }
}
