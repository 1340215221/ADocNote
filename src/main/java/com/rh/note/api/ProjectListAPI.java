package com.rh.note.api;

import com.rh.note.model.component.ProjectChooserImpl;
import com.rh.note.model.component.ProjectListFrameImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * 项目列表接口
 */
public class ProjectListAPI {
    /**
     * 导入新项目
     */
    public String selectProjectDirectory() {
        // 选择项目文件
        String projectPath = new ProjectChooserImpl().showOpenDialog();
        if (StringUtils.isBlank(projectPath)) {
            return null;
        }
        // 关闭旧
        new ProjectListFrameImpl().init().close();
        // todo 记录到打开记录中
        return projectPath;
    }
}
