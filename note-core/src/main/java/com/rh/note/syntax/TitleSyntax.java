package com.rh.note.syntax;

import com.rh.note.constants.RegexConstants;
import com.rh.note.sugar.TitleSyntaxSugar;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标题语法
 */
@Getter
@Setter
public class TitleSyntax {
    /**
     * 行内容匹配正则
     */
    private final static String regex = "^(=+)\\s+(" + RegexConstants.title_name_regex + ")\\s*$";
    /**
     * 级别
     */
    private int level;
    /**
     * 标题名
     */
    @NonNull
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

    public @NotNull TitleSyntax init(@NonNull TitleSyntaxSugar syntaxSugar) {
        level = syntaxSugar.getLevel();
        titleName = syntaxSugar.getTitleName();
        return this;
    }

    @Override
    public @NotNull String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < level; i++) {
            result.append("=");
        }
        result.append(" ").append(titleName);
        return result.toString();
    }
}
