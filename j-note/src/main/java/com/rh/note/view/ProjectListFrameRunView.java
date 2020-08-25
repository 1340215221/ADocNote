package com.rh.note.view;

import com.rh.note.builder.ProjectListFrameBuilder;
import com.rh.note.frame.ProjectListFrame;
import com.rh.note.view.Init;

import javax.swing.*;

/**
 * 项目列表窗口
 */
public class ProjectListFrameRunView extends Init<JFrame> {

    /**
     * 开启项目
     */
    public static void create() {
        new ProjectListFrame().start();
    }

    public ProjectListFrameRunView init() {
        return super.init(ProjectListFrameBuilder.getId());
    }

    /**
     * 关闭窗口
     */
    public void close() {
        getBean().setVisible(false);
    }

    private JFrame projectList() {
        return getBean();
    }

    /**
     * 显示窗口
     */
    public void show() {
        projectList().setVisible(true);
    }
}
