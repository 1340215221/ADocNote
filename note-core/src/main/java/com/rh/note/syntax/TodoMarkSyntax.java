package com.rh.note.syntax;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 待完成标记 语法
 */
public class TodoMarkSyntax {
    /**
     * 正则
     */
    private static final String regex = "//\\s*(?:todo|TODO)(?:\\s+(.*?))?\\s*$";
    /**
     * 内容
     */
    private String content;

    public @Nullable TodoMarkSyntax init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        content = matcher.group(1);
        return this;
    }
}
