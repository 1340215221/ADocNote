package com.rh.note.config;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.awt.AWTEvent;
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
}
