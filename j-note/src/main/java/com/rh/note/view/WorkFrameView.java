package com.rh.note.view;

import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.frame.WorkFrame;
import com.rh.note.util.Init;

import javax.swing.JFrame;

/**
 * 工作窗口视图
 */
public class WorkFrameView extends Init<JFrame> {

    public static void create() {
        new WorkFrame().start();
    }

    public WorkFrameView init() {
        return super.init(WorkFrameBuilder.id());
    }

    /**
     * 显示
     */
    public void show() {
        workFrame().setVisible(true);
    }

    private JFrame workFrame() {
        return getBean();
    }
}
