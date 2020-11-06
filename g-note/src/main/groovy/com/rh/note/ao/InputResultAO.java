package com.rh.note.ao;

import com.rh.note.base.Init;
import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.RequestParamsValidException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.event.MenuKeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
    private Character keyChar;
    /**
     * 时间时间
     */
    private Long when;
    /**
     * keycode
     */
    private Integer keyCode;
    /**
     * 按键
     */
    private Integer modifiers;
    /**
     * 编辑控件
     */
    private AdocTextPane textPane;

    public @Nullable ActionEvent getActionEvent() {
        if (textPane == null || keyChar == null || when == null || modifiers == null) {
            return null;
        }
        return new ActionEvent(textPane, eventId, keyChar.toString(), when, modifiers);
    }

    public AdocTextPane getTextPane() {
        return textPane;
    }

    public <T extends Init<AdocTextPane>> @NotNull InputResultAO copy(T textPane) {
        if (textPane != null) {
            this.textPane = textPane.getBean();
        }
        return this;
    }

    public void checkRequiredItems() {
        if (textPane == null || keyChar == null || when == null || modifiers == null) {
            throw new RequestParamsValidException();
        }
    }

    public void copy(MenuKeyEvent event) {
        if (event == null) {
            return;
        }

        keyChar = event.getKeyChar();
        when = event.getWhen();
        modifiers = event.getModifiers();
        keyCode = event.getKeyCode();
    }

    /**
     * 获得一个键盘事件
     */
    public @Nullable KeyEvent getKeyEvent() {
        if (textPane == null || when == null || modifiers == null || keyCode == null || keyChar == null) {
            return null;
        }
        return new KeyEvent(textPane, 402, when, modifiers, keyCode, keyChar);
    }
}
