package com.rh.note.event;

import org.apache.commons.lang3.StringUtils;

import static com.rh.note.config.BridgingBeanConfig.proManageAction;
import static com.rh.note.config.BridgingBeanConfig.operationAction;

/**
 * 项目列表 事件
 */
public class HistoryProjectListEvent {

    /**
     * 点击历史项目列表
     */
    public static void clicked_history_project_list() {
        String selectedProjectPath = operationAction().clickedHistoryProjectList();
        if (StringUtils.isBlank(selectedProjectPath)) {
            return;
        }
        proManageAction().openSelectedHistoryProject(selectedProjectPath);
    }

}
