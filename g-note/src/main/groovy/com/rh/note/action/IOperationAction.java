package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;

/**
 * 解析用户操作--入口
 */
public interface IOperationAction {
    /**
     * 点击历史项目
     */
    ClickedHistoryProjectListAO clickedHistoryProjectList(@NotNull MouseEvent mouseEvent);
}
