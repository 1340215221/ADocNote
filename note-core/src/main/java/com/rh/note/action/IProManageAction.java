package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import org.jetbrains.annotations.NotNull;

/**
 * 项目管理--入口
 */
public interface IProManageAction {
    /**
     * 打开项目, 通过路径
     */
    void openProjectByPath(@NotNull ClickedHistoryProjectListAO ao);

    /**
     * 启动窗口
     */
    void startFrame();
}
