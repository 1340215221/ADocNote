package com.rh.note.event

import com.rh.note.action.IProjectListAction
import com.rh.note.config.ActionConfig

/**
 * 项目管理菜单
 */
class ProjectManagerMenuEvent{

    private static final IProjectListAction projectListAction = ActionConfig.get.projectListAction()

    /**
     * 导入新项目
     */
    static def import_project = {
        projectListAction.importProject()
    }

}
