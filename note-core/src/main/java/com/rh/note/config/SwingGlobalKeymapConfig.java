package com.rh.note.config;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.event.WorkFrameEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 设置全局快捷键配置
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class SwingGlobalKeymapConfig {

    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private final List<AWTEventListener> list = new ArrayList<>(3);
    @Autowired
    private WorkFrameEvent event;

    /**
     * 设置快捷键监听事件
     */
    @PostConstruct
    public void init() {
         // alt + w
        AWTEventListener altWListener = e -> {
            if (KeymapConfig.altW(e)) {
                event.closeOtherTextPane();
            }
        };
        toolkit.addAWTEventListener(altWListener, AWTEvent.KEY_EVENT_MASK);
        // alt + q
        AWTEventListener altQListener = e -> {
            if (KeymapConfig.altQ(e)) {
                event.closeCurrentTextPane();
            }
        };
        toolkit.addAWTEventListener(altQListener, AWTEvent.KEY_EVENT_MASK);
        // ctrl + s
        AWTEventListener ctrlSListener = e -> {
            if (KeymapConfig.ctrlS(e)) {
                event.save_all_edit_text();
            }
        };
        toolkit.addAWTEventListener(ctrlSListener, AWTEvent.KEY_EVENT_MASK);
        // add to list
        list.add(altWListener);
        list.add(altQListener);
        list.add(ctrlSListener);
    }

    @PreDestroy
    public void destroy() {
        list.forEach(toolkit::removeAWTEventListener);
    }

}
