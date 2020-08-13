package com.rh.note.view;

import com.rh.note.builder.ProjectListFrameBuilder;
import com.rh.note.model.component.Init;

import javax.swing.*;

/**
 * 项目列表窗口
 */
public class ProjectListFrameView extends Init<JFrame> {

    public ProjectListFrameView init() {
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
}
