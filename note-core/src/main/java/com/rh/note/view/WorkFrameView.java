package com.rh.note.view;

import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.common.IFrameView;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

/**
 * 工作窗口视图
 */
public class WorkFrameView extends IFrameView<WorkFrameBuilder, JFrame> {

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
        return super.getComponent(WorkFrameBuilder::getWorkFrame);
    }

    /**
     * 关闭
     */
    public void close() {
        super.dispose();
    }
}
