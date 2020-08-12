package com.rh.note.action

import com.rh.note.api.FileAPIService
import com.rh.note.build.ActionBuild
import com.rh.note.model.component.ProjectListFrameImpl
import com.rh.note.model.component.ProjectListImpl
import com.rh.note.util.ISwingBuilder
import com.rh.note.view.ProjectList
import com.rh.note.vo.RecentlyOpenedRecordVO
import org.apache.commons.lang3.StringUtils

class ProjectListAction implements ISwingBuilder, ActionBuild {

    FileAPIService fileAPIService

    /**
     * 打开项目
     */
    void openProject() {
        def projectList = new ProjectListImpl().init()
        String projectPath = projectList.getSelectedProject()
        workAction.setProjectPath(projectPath)
        new ProjectListFrameImpl().init()?.close()
        workAction.openFrame()
    }

    /**
     * 设置项目列表
     */
    void setProjectList() {
        RecentlyOpenedRecordVO[] projectInfos = fileAPIService.writeOpenRecord()
        swingBuilder."${ProjectList.id}".listData = projectInfos
    }
}
