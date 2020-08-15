package com.rh.note.common;

import com.rh.note.file.AdocFile;
import com.rh.note.file.ProjectDirectory;

/**
 * 文件接口
 */
public interface IAdocFile {

    /**
     * 获得文件绝对路径
     */
    default String getAbsolutePath() {
        return new ProjectDirectory().getAbsolutePath() + getFilePath();
    }

    /**
     * 获得文件路径
     */
    String getFilePath();

    /**
     * 设置文件路径
     */
    <T extends IAdocFile> T setFilePath(String filePath);

    /**
     * 复制属性
     */
    default void copy(AdocFile adocFile) {}

}
