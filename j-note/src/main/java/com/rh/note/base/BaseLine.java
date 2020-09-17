package com.rh.note.base;

import com.rh.note.bean.IBaseLine;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import lombok.Data;

/**
 * 基础行属性
 */
@Data
public abstract class BaseLine implements IBaseLine {
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
