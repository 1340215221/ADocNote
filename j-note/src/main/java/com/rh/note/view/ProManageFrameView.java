package com.rh.note.view;

import com.rh.note.builder.ProjectManagementFrameBuilder;
import com.rh.note.frame.ProjectManageFrame;
import com.rh.note.util.Init;

import javax.swing.JFrame;

/**
 * 项目管理视图
 */
public class ProManageFrameView extends Init<JFrame> {

    public static void create() {
        new ProjectManageFrame().start();
    }

    public ProManageFrameView init() {
        return super.init(ProjectManagementFrameBuilder.id());
    }

    /**
     * 显示窗口
     */
    public void show() {
        proManageFrame().setVisible(true);
    }

    protected JFrame proManageFrame() {
        return getBean();
    }
}
