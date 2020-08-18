package com.rh.note.action;

import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.ProjectListViewAPI;
import com.rh.note.aspect.DoActionLog;
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
    @DoActionLog("打开项目")
    public void openProject() {
        ProjectListView projectList = new ProjectListView().init();
        String projectPath = projectList.getSelectedProject();
        fileAPIService.setProjectPath(projectPath);
        new ProjectListFrameRunView().init().close();
        workAction.openFrame();
    }

    /**
     * 导入新项目
     */
    @DoActionLog("导入新项目")
    public void importProject() {
        String projectPath = projectListAPI.selectProjectDirectory();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }
        fileAPIService.setProjectPath(projectPath);
        workAction.initProjectStructure();
        workAction.openFrame();
        fileAPIService.addOpenProjectRecord(projectPath);
    }

    /**
     * 启动窗口
     */
    @DoActionLog("启动窗口")
    public void startFrame() {
        // 加载数据
        RecentlyOpenedRecordVO[] voArr = fileAPIService.writeOpenRecord();
        // 构建控件
        projectListAPI.loadMainFrame();
        // 设置数据并展示
        projectListAPI.showFrame(voArr);
    }
}
