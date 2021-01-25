package com.rh.note.syntax;

import com.rh.note.constants.RegexConstants;
import lombok.Data;

/**
 * 标题语法
 */
@Data
public class TitleSyntax {
    /**
     * 行内容匹配正则
     */
    private final static String regex = "^(=+)\\s([" + RegexConstants.file_name_regex + "]+?)\\s*$";
    /**
     * 级别
     */
    private Integer level;
    /**
     * 标题名
     */
    private String titleName;
}
