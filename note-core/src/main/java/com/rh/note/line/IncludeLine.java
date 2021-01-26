package com.rh.note.line;

import com.rh.note.path.IncludeBeanPath;
import com.rh.note.syntax.IncludeSyntax;
import lombok.Data;

/**
 * 引用行
 */
@Data
public class IncludeLine {
    /**
     * 对象路径
     */
    private IncludeBeanPath beanPath;
    /**
     * include语法
     */
    private IncludeSyntax includeSyntax;
    /**
     * 父标题
     */
    private TitleLine parentTitle;
}
