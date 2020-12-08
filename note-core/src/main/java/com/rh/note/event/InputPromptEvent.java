package com.rh.note.event;

import com.rh.note.action.IWorkAction;
import com.rh.note.annotation.WorkSingleton;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.MouseEvent;

/**
 * 输入提示 事件
 */
@WorkSingleton
public class InputPromptEvent {

    @Autowired
    private IWorkAction workAction;

    public void selectItem(MouseEvent event) {
        workAction.replacePromptItem(event);
    }
}
