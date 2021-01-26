package com.rh.note.syntax;

import com.rh.note.constants.RegexConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标题语法
 */
@Data
public class TitleSyntax {
    /**
     * 行内容匹配正则
     */
    private final static String regex = "^(=+)\\s+(" + RegexConstants.title_name_regex + ")\\s*$";
    /**
     * 级别
     */
    private Integer level;
    /**
     * 标题名
     */
    private String titleName;

    public @Nullable TitleSyntax init(String lineText) {
        if (StringUtils.isBlank(lineText)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineText);
        if (!matcher.find()) {
            return null;
        }
        level = matcher.group(1).length();
        titleName = matcher.group(2);
        return this;
    }
}
