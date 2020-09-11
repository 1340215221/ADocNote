package com.rh.note.event;

import static com.rh.note.config.BridgingBeanConfig.proManageAction;

/**
 * 项目列表 事件
 */
public class HistoryProjectListEvent {

    /**
     * 点击历史项目列表
     */
    public static void clicked_history_project_list() {
        proManageAction().openSelectedHistoryProject();
    }

}
