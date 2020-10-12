package com.rh.note.syntax;

import com.rh.note.constants.BaseConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 标题语法
 */
@Data
public class TitleSyntax {

    /**
     * 匹配正则
     * todo
     */
    private final static String regex = "^(=+)\\s([" + BaseConstants.file_name_regex + "]+?)\\s*$";
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

    /**
     * 语法块文本
     */
    public @NotNull String toString() {
        StringBuilder str = new StringBuilder();
        Stream.generate(() -> "=").limit(level).forEach(str::append);
        str.append(" ")
                .append(titleName);
        return str.toString();
    }

}
