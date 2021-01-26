package com.rh.note.path;

import lombok.Data;

/**
 * 标题对象路径
 */
@Data
public class TitleBeanPath {
    /**
     * 文件相对路径
     */
    private String filePath;
    /**
     * 文件内标题路径
     */
    private String titlePath;
    /**
     * 行号
     */
    private int lineNumber;
}
