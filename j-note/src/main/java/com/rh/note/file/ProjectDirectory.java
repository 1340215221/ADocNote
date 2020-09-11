package com.rh.note.file;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目目录
 */
public class ProjectDirectory {

    /**
     * 项目目录绝对路径
     */
    private static String projectPath;

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        if (StringUtils.isBlank(ProjectDirectory.projectPath)) {
            ProjectDirectory.projectPath = projectPath;
        }
    }
}
