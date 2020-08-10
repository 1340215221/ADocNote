package com.rh.note.event

import com.rh.note.build.ActionBuild
import com.rh.note.vo.RecentlyOpenedRecordVO

import javax.swing.JList
import java.awt.event.MouseEvent

/**
 * 项目列表
 */
class ProjectListEvent implements ActionBuild {
    /**
     * 项目列表点击事件
     */
    static def project_list_mouse_client = { MouseEvent event ->
        if (event.clickCount < 2) {
            return
        }

        def list = event.source as JList
        def value = list.selectedValue as RecentlyOpenedRecordVO

        projectListAction.openProject(value.projectPath)
    }
}
