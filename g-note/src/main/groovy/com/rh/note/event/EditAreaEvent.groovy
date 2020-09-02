package com.rh.note.event

import com.rh.note.action.IWorkAction
import com.rh.note.config.ActionConfig

/**
 * 编辑区事件
 */
class EditAreaEvent {

    private static IWorkAction workAction() {
        ActionConfig.get.workAction()
    }

    /**
     * 选择已打开文明标签事件
     */
    static def select_open_file_tab = {
        workAction().loadTitleNavigateBySelectedTab()
    }

}
