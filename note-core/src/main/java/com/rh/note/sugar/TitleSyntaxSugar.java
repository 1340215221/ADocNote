package com.rh.note.sugar;

import com.rh.note.constants.RegexConstants;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标题快捷语法
 */
@Getter
public class TitleSyntaxSugar {
    /**
     * 行内容匹配正则
     */
    private final static String regex = "^=([0-9]+)\\s+(" + RegexConstants.title_name_regex + ")\\s*$";
    /**
     * 标题级别
     */
    private int level;
    /**
     * 标题名
     */
    @NonNull
    private String titleName;

    public @Nullable TitleSyntaxSugar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        level = Integer.parseInt(matcher.group(1));
        titleName = matcher.group(2);
        return this;
    }
}
