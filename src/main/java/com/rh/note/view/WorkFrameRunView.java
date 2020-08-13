package com.rh.note.view;

import com.rh.note.file.ProjectDirectory;
import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.frame.WorkFrame;
import com.rh.note.view.Init;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * 工作窗口
 */
public class WorkFrameRunView extends Init<JFrame> {

    private static ProjectDirectory projectInfo = new ProjectDirectory();

    public static void create() {
        new WorkFrame().start();
    }

    public static void setAbsolutePath(String absolutePath) {
        if (StringUtils.isNotBlank(absolutePath) && StringUtils.isBlank(projectInfo.getAbsolutePath())) {
            projectInfo.setAbsolutePath(absolutePath);
        }
    }

    public WorkFrameRunView init() {
        return super.init(WorkFrameBuilder.getId());
    }

    private JFrame workFrame() {
        return getBean();
    }

    /**
     * 显示窗口
     */
    public void show() {
        workFrame().setVisible(true);
    }
}
