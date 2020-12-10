package com.rh.note.view;

import com.rh.note.builder.ProManageFrameBuilder;
import com.rh.note.common.ISingletonView;
import com.rh.note.frame.ProjectManageFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

/**
 * 项目管理视图
 */
public class ProManageFrameView extends ISingletonView<ProManageFrameBuilder, JFrame> {

    public static void create() {
        new ProjectManageFrame().start();
    }

    @Override
    public @NotNull ProManageFrameView init() {
        return super.init();
    }

    /**
     * 显示窗口
     */
    public void show() {
        proManageFrame().setVisible(true);
    }

    /**
     * todo private
     */
    public @NotNull JFrame proManageFrame() {
        return super.getComponent(ProManageFrameBuilder::getFrame);
    }

    /**
     * 关闭窗口
     */
    public void close() {
        proManageFrame().dispose();
    }
}
