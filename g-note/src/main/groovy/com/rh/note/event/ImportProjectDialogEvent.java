package com.rh.note.event;

import static com.rh.note.config.BridgingBeanConfig.proManageAction;

/**
 * 选择项目弹窗事件
 */
public class ImportProjectDialogEvent {

    /**
     * 选择项目文件目录
     */
    public void select_project_directory() {
        String filePath = "";
        proManageAction().openProjectByDirectoryPath(filePath);
    }

}
