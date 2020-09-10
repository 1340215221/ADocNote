package com.rh.note.line;

import com.rh.note.file.AdocFile;
import lombok.Getter;

import java.util.List;

/**
 * 引用行
 */
@Getter
public class IncludeLine {

    /**
     * 所属文件
     */
    private AdocFile adocFile;
    /**
     * 行号
     */
    private Integer lineNumber;
    /**
     * 父标题
     */
    private TitleLine parentTitle;
    /**
     * 指向文件
     */
    private AdocFile targetFile;

}
