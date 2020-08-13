package com.rh.note.action;

import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.ProjectListViewAPI;
import com.rh.note.view.ProjectListFrameRunView;
import com.rh.note.view.ProjectListView;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
public class ProjectListAction {

    private FileServiceAPI fileAPIService;
    private ProjectListViewAPI projectListAPI;
    private WorkAction workAction;

    /**
     * 打开项目
     */
    public void openProject() {
        ProjectListView projectList = new ProjectListView().init();
        String projectPath = projectList.getSelectedProject();
        workAction.setProjectPath(projectPath);
        new ProjectListFrameRunView().init().close();
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

    /**
     * 显示项目管理窗口
     */
    public void showFrame() {
        projectListAPI.showFrame();
    }

    /**
     * 启动窗口
     */
    public void startFrame() {
        // 加载数据
        RecentlyOpenedRecordVO[] voArr = fileAPIService.writeOpenRecord();
        // 构建控件
        projectListAPI.loadMainFrame();
        // 设置数据并展示
        projectListAPI.showFrame(voArr);
    }
}
