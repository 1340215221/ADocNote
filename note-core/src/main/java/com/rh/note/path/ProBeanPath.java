package com.rh.note.path;

/**
 * 项目地址
 */
public class ProBeanPath {

    /**
     * 项目绝对路径
     */
    private static String projectPath;

    public ProBeanPath setProjectPath(String projectPath) {
        ProBeanPath.projectPath = projectPath;
        return this;
    }

    public String getProjectPath() {
        return projectPath;
    }

}
