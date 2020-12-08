package com.rh.note.view;

import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.common.IView;
import com.rh.note.frame.WorkFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

/**
 * 工作窗口视图
 */
public class WorkFrameView extends IView<JFrame> {

    public static void create() {
        new WorkFrame().start();
    }

    @Override
    public @NotNull WorkFrameView init() {
        return super.init();
    }

    /**
     * 显示
     */
    public void show() {
        workFrame().setVisible(true);
    }

    private @NotNull JFrame workFrame() {
        WorkFrameBuilder builder = getBuilder();
        return builder.getWorkFrame();
    }

    /**
     * 关闭
     */
    public void close() {
        workFrame().dispose();
        System.exit(0);
    }

    /**
     * beanName
     */
    protected @NotNull String getBuilderName() {
        return WorkFrameBuilder.id();
    }
}
