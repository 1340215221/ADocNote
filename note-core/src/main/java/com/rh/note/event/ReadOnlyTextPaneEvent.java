package com.rh.note.event;

import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 只读编辑区 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class ReadOnlyTextPaneEvent {

    @Autowired
    private WorkAction workAction;

    /**
     * 刷新语法高亮
     */
    public void refresh_syntax_highlight(String filePath) {
        workAction.refreshSyntaxHighlightOfJavaTextPaneByFilePath(filePath);
    }
}