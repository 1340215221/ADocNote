package com.rh.note.common;

/**
 * 文件接口
 */
public interface IAdocFile {

    /**
     * 获得文件路径
     */
    String getFilePath();

    /**
     * 设置文件路径
     */
    <T extends IAdocFile> T setFilePath(String filePath);

}
