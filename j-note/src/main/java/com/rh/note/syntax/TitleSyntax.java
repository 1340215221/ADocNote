package com.rh.note.syntax;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标题语法
 */
@Data
public class TitleSyntax {

    /**
     * 匹配正则
     * todo
     */
    private final static String regex = "^(=+)\\s(\\S+)\\s*$";
    /**
     * 级别
     */
    private Integer level;
    /**
     * 标题
     */
    private String titleName;

    public TitleSyntax init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        level = matcher.group(1).length();
        titleName = matcher.group(2);
        return this;
    }

}
