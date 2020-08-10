package com.rh.note.action

import com.rh.note.api.FileAPIService
import com.rh.note.build.LoaderBuild
import com.rh.note.factory.WorkFrameFactory
import com.rh.note.load.ProjectListLoader
import com.rh.note.util.ISwingBuilder
import com.rh.note.view.ProjectList
import com.rh.note.vo.RecentlyOpenedRecordVO
import org.apache.commons.lang3.StringUtils

class ProjectListAction implements ISwingBuilder, LoaderBuild {

    FileAPIService fileAPIService

    void openProject(String projectPath) {
        if (StringUtils.isBlank(projectPath)) {
            return
        }
        workLoader.setProjectPath(projectPath)
        projectListLoader.closeFrame()
        workLoader.openFrame()
    }

    /**
     * 设置项目列表
     */
    void setProjectList() {
        RecentlyOpenedRecordVO[] projectInfos = fileAPIService.writeOpenRecord()
        swingBuilder."${ProjectList.id}".listData = projectInfos
    }
}
