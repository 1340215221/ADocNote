package com.rh.note.event;

import com.rh.note.ao.ClickedHistoryProjectListAO;

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
        ClickedHistoryProjectListAO ao = operationAction().clickedHistoryProjectList(mouseEvent);
        if (ao == null) {
            return;
        }
        proManageAction().openProjectByPath(ao);
    }

}
