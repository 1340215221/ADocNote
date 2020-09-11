package com.rh.note.syntax;

import lombok.Data;

/**
 * 标题语法
 */
@Data
public class TitleSyntax {

    /**
     * 匹配正则
     * todo
     */
    private final static String regex = "";
    /**
     * 级别
     */
    private Integer level;
    /**
     * 缩进
     */
    private String indented;
    /**
     * 标题
     */
    private String titleName;

    public TitleSyntax init(String lineContent) {
        //todo
        return null;
    }

}
