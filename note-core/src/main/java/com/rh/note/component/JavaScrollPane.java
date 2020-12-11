package com.rh.note.component;

import org.apache.commons.lang3.StringUtils;

import javax.swing.JScrollPane;

/**
 * java文件滚动
 */
public class JavaScrollPane extends JScrollPane {

    /**
     * 绝对路径
     */
    private String absolutePath;

    /**
     * 文件名
     * todo
     */
    public String getFileName() {
        if (StringUtils.isBlank(absolutePath)) {
            return null;
        }
        int index = absolutePath.lastIndexOf('/');
        if (index < 0) {
            return absolutePath;
        }
        return absolutePath.substring(index + 1);
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
