package com.rh.note.file;

import com.sun.org.apache.bcel.internal.generic.POP;
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
        ProjectDirectory.projectPath = this.handle(projectPath);
    }

    /**
     * 处理地址
     */
    private String handle(String filePath) {
        filePath = coverToLinuxPath(filePath);
        filePath = completeEnd(filePath);
        return filePath;
    }

    /**
     * 目录地址统一以 "/" 结尾
     */
    private String completeEnd(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return "";
        }
        if ('/' == filePath.charAt(filePath.length() - 1)) {
            return filePath;
        }
        return filePath + "/";
    }

    /**
     * 将windows地址转为linux地址
     */
    private String coverToLinuxPath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return "";
        }
        if (!filePath.contains("\\")) {
            return filePath;
        }
        return filePath.replaceAll("\\\\", "/");
    }
}
