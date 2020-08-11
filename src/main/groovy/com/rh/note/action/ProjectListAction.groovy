package com.rh.note.action

import com.rh.note.api.FileAPIService
import com.rh.note.build.ActionBuild
import com.rh.note.model.component.ProjectListFrameImpl
import com.rh.note.util.ISwingBuilder
import com.rh.note.view.ProjectList
import com.rh.note.vo.RecentlyOpenedRecordVO
import org.apache.commons.lang3.StringUtils

class ProjectListAction implements ISwingBuilder, ActionBuild {

    FileAPIService fileAPIService

    /**
     * 打开项目
     */
    void openProject(String projectPath) {
        if (StringUtils.isBlank(projectPath)) {
            return
        }
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
