package com.rh.note.event;

import static com.rh.note.config.BridgingBeanConfig.proManageAction;

/**
 * 导入按钮事件
 */
public class ImportProjectButtonEvent {

    /**
     * 选择项目, 通过项目目录
     */
    public static void clicked_import_project_button() {
        String projectPath = proManageAction().openDialogForSelectProject();
        proManageAction().openSelectedHistoryProject(projectPath);
    }

}