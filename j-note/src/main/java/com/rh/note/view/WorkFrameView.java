package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.frame.WorkFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

/**
 * 工作窗口视图
 */
public class WorkFrameView extends Init<JFrame> {

    public static void create() {
        new WorkFrame().start();
    }

    public @NotNull WorkFrameView init() {
        return super.init(WorkFrameBuilder.id());
    }

    /**
     * 显示
     */
    public void show() {
        workFrame().setVisible(true);
    }

    private @NotNull JFrame workFrame() {
        return getBean();
    }

    /**
     * 关闭
     */
    public void close() {
        workFrame().dispose();
        System.exit(0);
    }
}
