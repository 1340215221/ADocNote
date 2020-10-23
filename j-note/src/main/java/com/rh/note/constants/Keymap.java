package com.rh.note.constants;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 快捷键解析
 */
public interface Keymap {

    /**
     * ctrl s
     * 全局保存
     */
    static boolean isSaveOperation(@NonNull AWTEvent event) {
        if (!(event instanceof KeyEvent) || event.getID() != KeyEvent.KEY_PRESSED) {
            return false;
        }
        KeyEvent keyEvent = (KeyEvent) event;
        return keyEvent.getKeyCode() == 83 && keyEvent.getModifiers() == 2;
    }

    /**
     * ctrl 点击
     * 进入include指向文件
     */
    static boolean isEnterInclude(@NonNull MouseEvent event) {
        return event.getModifiers() == 18;
    }

    /**
     * 双击 (点击次数大于一)
     */
    static boolean isDoubleClick(@NonNull MouseEvent event) {
        return event.getClickCount() > 1;
    }

    /**
     * shift F6
     * 重命名
     */
    static boolean isRename(@NonNull KeyEvent event) {
        return event.getKeyCode() == 117 && event.getModifiers() == 1;
    }

    /**
     * alt + del
     * 安全删除
     */
    static boolean isSafeDelete(@NotNull KeyEvent event) {
        return event.getKeyCode() == 127 && event.getModifiers() == 8;
    }

    /**
     * ctrl + alt + m
     * 下沉标题
     */
    static boolean isSinkTitle(@NonNull KeyEvent event) {
        return event.getKeyCode() == 77 && event.getModifiers() == 10;
    }
}
