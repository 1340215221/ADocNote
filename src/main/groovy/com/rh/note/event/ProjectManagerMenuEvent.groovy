package com.rh.note.event

import com.rh.note.build.ActionBuild
import com.rh.note.util.ISwingBuilder

/**
 * 项目管理菜单
 */
class ProjectManagerMenuEvent implements ISwingBuilder, ActionBuild{

    static def import_project = {
        projectListAction.importProject()
    }

}
