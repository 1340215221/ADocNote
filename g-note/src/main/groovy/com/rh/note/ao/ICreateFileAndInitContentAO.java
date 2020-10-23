package com.rh.note.ao;

/**
 * 创建文件和初始化内容
 */
public interface ICreateFileAndInitContentAO {
    /**
     * 获得文件绝对路径
     */
    String getAbsolutePath();

    /**
     * 获得文件内容
     */
    String getText();
}
