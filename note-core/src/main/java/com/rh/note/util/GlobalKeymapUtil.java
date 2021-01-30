package com.rh.note.util;

import com.rh.note.config.KeymapConfig;
import lombok.NonNull;

import java.awt.*;
import java.awt.event.AWTEventListener;

/**
 * 全局快捷键事件 工具
 */
public class GlobalKeymapUtil {

    private final Toolkit toolkit;

    public GlobalKeymapUtil() {
        toolkit = Toolkit.getDefaultToolkit();
    }

    /**
     * alt + w
     */
    public void event_alt_W(@NonNull Runnable run) {
        AWTEventListener listener = event -> {
            if (KeymapConfig.altW(event)) {
                run.run();
            }
        };
        toolkit.addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * alt + q
     */
    public void event_alt_Q(@NonNull Runnable run) {
        AWTEventListener listener = event -> {
            if (KeymapConfig.altQ(event)) {
                run.run();
            }
        };
        toolkit.addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * ctrl + s 事件
     */
    public void event_ctrl_S(@NonNull Runnable run) {
        AWTEventListener listener = event -> {
            if (KeymapConfig.ctrlS(event)) {
                run.run();
            }
        };
        toolkit.addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
    }
}
