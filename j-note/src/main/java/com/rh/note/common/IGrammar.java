package com.rh.note.common;

import com.rh.note.file.ProjectDirectory;

/**
 * 语法接口
 */
public interface IGrammar {

    /**
     * 获取行号
     */
    Integer getLineNumber();

    /**
     * 获得文件路径
     */
    String getFilePath();

    /**
     * 获得文件绝对路径
     */
    default String getAbsolutePath() {
        return new ProjectDirectory().getAbsolutePath() + getFilePath();
    }

    /**
     * 转换为行内容
     */
    String toLineContent();
}
