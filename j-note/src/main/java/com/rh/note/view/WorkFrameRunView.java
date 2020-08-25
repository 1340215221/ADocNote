package com.rh.note.view;

import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.frame.WorkFrame;

import javax.swing.*;

/**
 * 工作窗口
 */
public class WorkFrameRunView extends Init<JFrame> {

    public static void create() {
        new WorkFrame().start();
    }

    public WorkFrameRunView init() {
        return super.init(WorkFrameBuilder.getId());
    }

    private JFrame workFrame() {
        return getBean();
    }

    /**
     * 显示窗口
     */
    public void show() {
        workFrame().setVisible(true);
    }
}
