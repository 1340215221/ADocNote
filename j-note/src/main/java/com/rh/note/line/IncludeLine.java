package com.rh.note.line;

import com.rh.note.base.BaseLine;
import com.rh.note.file.AdocFile;
import com.rh.note.syntax.IncludeSyntax;
import lombok.Getter;

/**
 * 引用行
 */
@Getter
public class IncludeLine extends BaseLine {
    /**
     * 指向文件
     */
    private AdocFile targetFile;
    /**
     * 语法对象
     */
    private IncludeSyntax includeSyntax;
}
