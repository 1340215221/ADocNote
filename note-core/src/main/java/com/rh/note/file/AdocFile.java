package com.rh.note.file;

import com.rh.note.line.TitleLine;
import lombok.Data;

import java.util.List;

/**
 * adoc文件
 */
@Data
public class AdocFile {
    /**
     * 文件绝对路径
     */
    private String;
    /**
     * 文件相对路径
     */
    private String;
    /**
     * 标题列表
     */
    private List<TitleLine>;
    /**
     * 引用列表
     */
}
