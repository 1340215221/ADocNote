package com.rh.note.action;

import com.rh.note.api.FileAPIService;
import com.rh.note.api.ProjectListAPI;
import com.rh.note.build.ActionBuild;
import com.rh.note.model.component.ProjectListFrameImpl;
import com.rh.note.model.component.ProjectListImpl;
import com.rh.note.util.ISwingBuilder;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.StringUtils;

class ProjectListAction implements ISwingBuilder, ActionBuild {

    FileAPIService fileAPIService;
    ProjectListAPI projectListAPI;

    /**
     * 打开项目
     */
    public void openProject() {
        ProjectListImpl projectList = new ProjectListImpl().init();
        String projectPath = projectList.getSelectedProject();
        workAction.setProjectPath(projectPath);
        new ProjectListFrameImpl().init().close();
        workAction.openFrame();
    }

    /**
     * 设置项目列表
     */
    public void setProjectList() {
        RecentlyOpenedRecordVO[] projectInfos = fileAPIService.writeOpenRecord();
        new ProjectListImpl().init().loadProjectList(projectInfos);
    }

    /**
     * 导入新项目
     */
    public void importProject() {
        String projectPath = projectListAPI.selectProjectDirectory();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }
        workAction.setProjectPath(projectPath);
        workAction.initProjectStructure();
        workAction.openFrame();
    }
}
