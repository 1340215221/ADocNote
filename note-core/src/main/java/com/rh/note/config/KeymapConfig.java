package com.rh.note.config;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 快捷键 配置
 */
@Component
public class KeymapConfig {
    /**
     * alt w
     * 关闭其它编辑区
     */
    public static boolean altW(@NonNull AWTEvent event) {
        if (!(event instanceof KeyEvent) || event.getID() != KeyEvent.KEY_PRESSED) {
            return false;
        }
        KeyEvent keyEvent = (KeyEvent) event;
        return keyEvent.getKeyCode() == 87 && keyEvent.getModifiers() == 8;
    }
    /**
     * alt q
     * 关闭当前编辑区
     */
    public static boolean altQ(@NonNull AWTEvent event) {
        if (!(event instanceof KeyEvent) || event.getID() != KeyEvent.KEY_PRESSED) {
            return false;
        }
        KeyEvent keyEvent = (KeyEvent) event;
        return keyEvent.getKeyCode() == 81 && keyEvent.getModifiers() == 8;
    }
    /**
     * ctrl s
     * 全局保存
     */
    public static boolean ctrlS(@NonNull AWTEvent event) {
        if (!(event instanceof KeyEvent) || event.getID() != KeyEvent.KEY_PRESSED) {
            return false;
        }
        KeyEvent keyEvent = (KeyEvent) event;
        return keyEvent.getKeyCode() == 83 && keyEvent.getModifiers() == 2;
    }
    /**
     * 双击
     */
    public boolean doubleClick(@NonNull MouseEvent event) {
        return event.getClickCount() > 1;
    }

    /**
     * ctrl + 鼠标左键
     */
    public boolean ctrlLeftClick(@NonNull MouseEvent event) {
        return event.getModifiers() == 18;
    }

    /**
     * 回车
     */
    public boolean isEnter(@NonNull KeyEvent event) {
        return event.getModifiers() == 0 && event.getKeyCode() == 0 && '\n' == event.getKeyChar();
    }

    /**
     * 是重命名快捷键
     */
    public boolean isShiftF6(@NonNull KeyEvent event) {
        return event.getKeyCode() == 117 && event.getModifiers() == 1;
    }

    /**
     * 是安全删除
     */
    public boolean isAltDel(@NonNull KeyEvent event) {
        return event.getKeyCode() == 127 && event.getModifiers() == 8;
    }

    /**
     * ctrl + t
     */
    public boolean isCtrlT(@NonNull KeyEvent event) {
        return event.getKeyCode() == 84 && event.getModifiers() == 2;
    }

    /**
     * ctrl + 1
     */
    public boolean isCtrl1(@NonNull KeyEvent event) {
        return event.getKeyCode() == 49 && event.getModifiers() == 2;
    }
}
