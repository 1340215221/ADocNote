package com.rh.note.bean;

/**
 * adoc文件抽象对象
 */
public interface IAdocFile {
    /**
     * 获得项目路径
     */
    String getFilePath();

    ITitleLine getRootTitle();
}
