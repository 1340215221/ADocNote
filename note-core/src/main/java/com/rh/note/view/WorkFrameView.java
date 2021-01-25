package com.rh.note.view;

import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.common.BaseView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 工作窗口 视图
 */
public class WorkFrameView extends BaseView<WorkFrameBuilder, JFrame> {

    public @NotNull WorkFrameView init() {
        return super.init();
    }

    protected @NotNull JFrame frame() {
        return super.getComponent(WorkFrameBuilder::getFrame);
    }
}
