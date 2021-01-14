package com.rh.note.config;

import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;

/**
 * 快捷键 配置
 */
@Component
public class KeymapConfig {
    /**
     * 双击
     */
    public boolean doubleClick(MouseEvent event) {
        return event.getClickCount() > 1;
    }
}
