package com.rh.note.ao;

import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.RequestParamsValidException;
import org.apache.commons.lang3.StringUtils;

import javax.swing.event.MenuKeyEvent;
import java.awt.event.ActionEvent;

/**
 * 输入值 参数
 */
public class InputResultAO {
    /**
     * 输入值
     */

    /**
     * 编辑控件
     */
    private AdocTextPane textPane;

    public ActionEvent getEvent() {
        return event;
    }

    public InputResultAO setEvent(ActionEvent event) {
        this.event = event;
        return this;
    }

    public AdocTextPane getTextPane() {
        return textPane;
    }

    public InputResultAO setTextPane(AdocTextPane textPane) {
        this.textPane = textPane;
        return this;
    }

    public void checkRequiredItems() {
        if (event == null || textPane == null) {
            throw new RequestParamsValidException();
        }
    }

    public void copy(MenuKeyEvent event) {
        if (event == null) {
            return;
        }

        new ActionEvent(textPane, 1001, inputStr, event.when, event.modifiers);

    }
}
