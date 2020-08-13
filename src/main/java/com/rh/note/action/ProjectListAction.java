package com.rh.note.action;

import com.rh.note.api.FileAPIService;
import com.rh.note.api.ProjectListAPI;
import com.rh.note.build.ActionBuild;
import com.rh.note.view.ProjectListFrameView;
import com.rh.note.view.ProjectListView;
import com.rh.note.util.ISwingBuilder;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
class ProjectListAction implements ISwingBuilder, ActionBuild {

    @NonNull
    private FileAPIService fileAPIService;
    @NonNull
    private ProjectListAPI projectListAPI;

    /**
     * 打开项目
     */
    public void openProject() {
        ProjectListView projectList = new ProjectListView().init();
        String projectPath = projectList.getSelectedProject();
        workAction.setProjectPath(projectPath);
        new ProjectListFrameView().init().close();
        workAction.openFrame();
    }

    /**
     * 设置项目列表
     */
    public void setProjectList() {
        RecentlyOpenedRecordVO[] projectInfos = fileAPIService.writeOpenRecord();
        new ProjectListView().init().loadProjectList(projectInfos);
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
