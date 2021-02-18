package com.rh.note.event;

import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.timer.RefreshSyntaxHighlightOfTextPaneSelectedTimer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * java编辑区 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class JavaTextPaneEvent {

    @Autowired
    RefreshSyntaxHighlightOfTextPaneSelectedTimer timer;
    @Autowired
    private WorkAction workAction;

    /**
     * 刷新语法高亮
     */
    public void refresh_syntax_highlight_by_timer(String filePath) {
        timer.cancelOnceRun();
        workAction.refreshSyntaxHighlightOfJavaTextPaneByFilePath(filePath);
    }
}