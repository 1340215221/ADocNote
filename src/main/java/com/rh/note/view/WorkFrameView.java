package com.rh.note.view;

import com.rh.note.file.ProjectDirectory;
import com.rh.note.builder.WorkFrameBuilder;
import com.rh.note.model.component.Init;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * 工作窗口
 */
public class WorkFrameView extends Init<JFrame> {

    private static ProjectDirectory projectInfo = new ProjectDirectory();

    public static void setAbsolutePath(String absolutePath) {
        if (StringUtils.isNotBlank(absolutePath) && StringUtils.isBlank(projectInfo.getAbsolutePath())) {
            projectInfo.setAbsolutePath(absolutePath);
        }
    }

    public WorkFrameView init() {
        return super.init(WorkFrameBuilder.getId());
    }

    private JFrame workFrame() {
        return getBean();
    }

}
