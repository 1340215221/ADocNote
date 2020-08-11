package com.rh.note.model.component;

import com.rh.note.view.ProjectListFrame;

import javax.swing.*;

/**
 * 项目列表窗口
 */
public class ProjectListFrameImpl extends Init<JFrame> {

    public ProjectListFrameImpl init() {
        return super.init(ProjectListFrame.getId());
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
}
