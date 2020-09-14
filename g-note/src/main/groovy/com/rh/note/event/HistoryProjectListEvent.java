package com.rh.note.event;

import org.apache.commons.lang3.StringUtils;

import java.awt.event.MouseEvent;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.proManageAction;

/**
 * 项目列表 事件
 */
public class HistoryProjectListEvent {

    /**
     * 点击历史项目列表
     */
    public static void clicked_history_project_list(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() < 2) {
            return;
        }
        String selectedProjectPath = operationAction().clickedHistoryProjectList();
        if (StringUtils.isBlank(selectedProjectPath)) {
            return;
        }
        proManageAction().openSelectedHistoryProject(selectedProjectPath);
    }

}
