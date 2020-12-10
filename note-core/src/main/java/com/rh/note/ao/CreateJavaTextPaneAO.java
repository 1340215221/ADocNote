package com.rh.note.ao;

import lombok.Data;

/**
 * 创建java编辑区 参数
 */
@Data
public class CreateJavaTextPaneAO {
    /**
     * java文件绝对路径
     */
    private String absolutePath;
    /**
     * 编辑区原文件路径
     */
    private String sourceFilePath;
    /**
     * 指向文件路径
     */
    private String includeFilePath;
    /**
     * 标记1 行号
     */
    private Integer markLineNumber1;
    /**
     * 标记2 行号
     */
    private Integer markLineNumber2;
}
