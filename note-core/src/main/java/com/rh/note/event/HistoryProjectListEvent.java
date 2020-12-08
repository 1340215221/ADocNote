package com.rh.note.event;

import com.rh.note.action.IOperationAction;
import com.rh.note.action.IProManageAction;
import com.rh.note.annotation.ProjectManage;
import com.rh.note.ao.ClickedHistoryProjectListAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.MouseEvent;

/**
 * 项目列表 事件
 */
@ProjectManage
public class HistoryProjectListEvent {

    @Autowired
    private IOperationAction operationAction;
    @Autowired
    private IProManageAction proManageAction;

    /**
     * 点击历史项目列表
     */
    public void clicked_history_project_list(MouseEvent mouseEvent) {
        ClickedHistoryProjectListAO ao = operationAction.clickedHistoryProjectList(mouseEvent);
        if (ao == null) {
            return;
        }
        proManageAction.openProjectByPath(ao);
    }

}
