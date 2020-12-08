package com.rh.note.event;

import com.rh.note.action.IOperationAction;
import com.rh.note.action.IWorkAction;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.ao.MarkLineAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.KeyEvent;

/**
 * java文件编辑区 事件
 */
@WorkSingleton
public class JTextPaneEvent {

    @Autowired
    private IOperationAction operationAction;
    @Autowired
    private IWorkAction workAction;

    /**
     * 标记行
     */
    public void markLine(KeyEvent event) {
        MarkLineAO ao = operationAction.getCareLineNumberForJavaTextPane(event);
        if (ao == null) {
            return;
        }
        workAction.markLine(ao);
        workAction.saveAllEdited();
    }
}
