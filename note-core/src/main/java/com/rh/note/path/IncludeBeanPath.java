package com.rh.note.path;

import lombok.Data;

/**
 * 引用对象路径
 */
@Data
public class IncludeBeanPath {
    /**
     * 行号
     */
    private Integer lineNumber;
    /**
     * 文件路径
     */
    private String filePath;
}
