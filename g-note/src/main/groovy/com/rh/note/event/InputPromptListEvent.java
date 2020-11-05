package com.rh.note.event;

import com.rh.note.ao.InputResultAO;

import javax.swing.event.MenuKeyEvent;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 输入提示 时间
 */
public class InputPromptListEvent {

    /**
     * 将普通值输入到编辑区
     * 0-9a-zA-Z\\.\\-_
     */
    public static void input_to_text_pane(MenuKeyEvent event) {
        InputResultAO ao = operationAction().checkInputSimpleChar(event);
        if (ao == null) {
            return;
        }
        workAction().inputToTextPane(ao);
    }

}
