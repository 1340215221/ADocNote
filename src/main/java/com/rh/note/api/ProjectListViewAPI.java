package com.rh.note.api;

import com.rh.note.view.ProjectChooserRunView;
import com.rh.note.view.ProjectListFrameRunView;
import com.rh.note.view.ProjectListView;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 项目列表接口
 */
public class ProjectListViewAPI {
    /**
     * 导入新项目
     */
    public String selectProjectDirectory() {
        // 选择项目文件
        String projectPath = ProjectChooserRunView.showOpenDialog();
        if (StringUtils.isBlank(projectPath)) {
            return null;
        }
        // 关闭旧
        new ProjectListFrameRunView().init().close();
        // todo 记录到打开记录中
        return projectPath;
    }

    /**
     * 显示项目管理窗口
     */
    public void showFrame() {
        new ProjectListFrameRunView().init().show();
    }

    /**
     * 加载主窗口
     */
    public void loadMainFrame() {
        ProjectListFrameRunView.create();
    }

    /**
     * 设置数据并展示
     */
    public void showFrame(RecentlyOpenedRecordVO[] voArr) {
        if (ArrayUtils.isEmpty(voArr)) {
            return;
        }
        new ProjectListView().init().loadProjectList(voArr);
        new ProjectListFrameRunView().init().show();
    }
}
