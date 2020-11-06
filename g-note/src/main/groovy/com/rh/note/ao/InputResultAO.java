package com.rh.note.ao;

import com.rh.note.base.Init;
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
     * event id
     */
    private final Integer eventId = 1001;
    /**
     * 输入值
     */
    private String inputStr;
    /**
     * 时间时间
     */
    private Long when;
    /**
     * 按键
     */
    private Integer modifiers;
    /**
     * 编辑控件
     */
    private AdocTextPane textPane;

    public ActionEvent getEvent() {
        if (textPane == null || StringUtils.isBlank(inputStr) || when == null || modifiers == null) {
            return null;
        }
        return new ActionEvent(textPane, eventId, inputStr, when, modifiers);
    }

    public AdocTextPane getTextPane() {
        return textPane;
    }

    public <T extends Init<AdocTextPane>> InputResultAO copy(T textPane) {
        if (textPane != null) {
            this.textPane = textPane.getBean();
        }
        return this;
    }

    public void checkRequiredItems() {
        if (textPane == null || StringUtils.isBlank(inputStr) || when == null || modifiers == null) {
            throw new RequestParamsValidException();
        }
    }

    public void copy(MenuKeyEvent event) {
        if (event == null) {
            return;
        }

        inputStr = event.getKeyChar() + "";
        when = event.getWhen();
        modifiers = event.getModifiers();
    }
}
