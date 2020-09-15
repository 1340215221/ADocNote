package com.rh.note.action;

import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;

/**
 * 默认事件操作入口
 */
public class DefaultEventAction implements IDefaultEventAction {
    @Override
    public void ctrlDelete(ActionEvent event) {

    }

    @Override
    public void enter(ActionEvent event) {
        new DefaultEditorKit.InsertBreakAction().actionPerformed(event);
    }
}
