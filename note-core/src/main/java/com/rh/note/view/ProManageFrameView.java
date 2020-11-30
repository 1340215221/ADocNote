package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.ProManageFrameBuilder;
import com.rh.note.frame.ProjectManageFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

/**
 * 项目管理视图
 */
public class ProManageFrameView extends Init<JFrame> {

    public static void create() {
        new ProjectManageFrame().start();
    }

    public @NotNull ProManageFrameView init() {
        return super.init(ProManageFrameBuilder.id());
    }

    /**
     * 显示窗口
     */
    public void show() {
        proManageFrame().setVisible(true);
    }

    private @NotNull JFrame proManageFrame() {
        return getBean();
    }

    /**
     * 关闭窗口
     */
    public void close() {
        proManageFrame().dispose();
    }
}
