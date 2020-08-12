package com.rh.note.model.component;

import com.rh.note.model.file.ProjectInfo;
import com.rh.note.view.WorkFrame;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * 工作窗口
 */
public class WorkFrameImpl extends Init<JFrame> {

    private static ProjectInfo projectInfo = new ProjectInfo();

    public static void setAbsolutePath(String absolutePath) {
        if (StringUtils.isNotBlank(absolutePath) && StringUtils.isBlank(projectInfo.getAbsolutePath())) {
            projectInfo.setAbsolutePath(absolutePath);
        }
    }

    public WorkFrameImpl init() {
        return super.init(WorkFrame.getId());
    }

    private JFrame workFrame() {
        return getBean();
    }

}
