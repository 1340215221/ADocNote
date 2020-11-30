package com.rh.note.base;

import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 基础行属性
 */
@Data
@Accessors(chain = true)
public abstract class BaseLine {
    /**
     * 所属文件
     */
    protected AdocFile adocFile;
    /**
     * 行号
     */
    protected Integer lineNumber;
    /**
     * 父标题
     */
    protected TitleLine parentTitle;
}
