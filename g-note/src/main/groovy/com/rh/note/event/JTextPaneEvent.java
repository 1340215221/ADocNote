package com.rh.note.event;

import com.rh.note.ao.MarkLineAO;

import java.awt.event.KeyEvent;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * java文件编辑区 事件
 */
public class JTextPaneEvent {
    /**
     * 标记行
     */
    public static void markLine(KeyEvent event) {
        MarkLineAO ao = operationAction().getCareLineNumberForJavaTextPane(event);
        if (ao == null) {
            return;
        }
        workAction().markLine(ao);
        workAction().saveAllEdited();
    }
}
