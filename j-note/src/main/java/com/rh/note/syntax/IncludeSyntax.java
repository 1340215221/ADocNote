package com.rh.note.syntax;

import lombok.Data;

/**
 * 引用语法
 */
@Data
public class IncludeSyntax {

    /**
     * 匹配正则
     * todo
     */
    private final static String regex = "";
    /**
     * 缩进
     */
    private String indented;
    /**
     * 指向相对路径
     */
    private String targetRelativePath;
    /**
     * 指向文件名
     */
    private String targetFileName;
    /**
     * 文件后缀
     */
    private String targetFileSuf;
    /**
     * 引用行,起始
     */
    private Integer lineStart;
    /**
     * 引用行,结束
     */
    private Integer lineEnd;

    /**
     * 通过行内容初始化
     */
    public IncludeSyntax init(String lienContent) {
        //todo
        return null;
    }

}
