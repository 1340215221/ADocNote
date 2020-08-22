package com.rh.note.event


import com.rh.note.action.ProjectListAction
import com.rh.note.config.BeanConfig

/**
 * 项目管理菜单
 */
class ProjectManagerMenuEvent{

    private static final ProjectListAction projectListAction = BeanConfig.projectListAction

    /**
     * 导入新项目
     */
    static def import_project = {
        projectListAction.importProject()
    }

}
