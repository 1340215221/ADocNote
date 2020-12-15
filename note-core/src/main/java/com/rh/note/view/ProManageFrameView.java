package com.rh.note.view;

import com.rh.note.builder.ProManageFrameBuilder;
import com.rh.note.common.IFrameView;
import com.rh.note.common.ISingletonView;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

/**
 * 项目管理视图
 */
public class ProManageFrameView extends IFrameView<ProManageFrameBuilder, JFrame> {

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
        super.dispose();
    }
}
