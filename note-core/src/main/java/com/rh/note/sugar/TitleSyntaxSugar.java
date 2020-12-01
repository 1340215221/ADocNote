package com.rh.note.sugar;

import com.rh.note.constants.BaseConstants;
import com.rh.note.syntax.TitleSyntax;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * title快捷语法
 */
public class TitleSyntaxSugar {

    private static final String regex = "^=([0-9])+ ([" + BaseConstants.file_name_regex + "]+?)\\s*$";
    /**
     * 级别
     */
    private Integer level;
    /**
     * 标题名
     */
    private String titleName;

    public @Nullable TitleSyntaxSugar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        level = Optional.ofNullable(matcher.group(1))
                .filter(str -> str.matches("[0-9]+"))
                .map(Integer::valueOf)
                .orElse(null);
        titleName = matcher.group(2);
        return this;
    }

    /**
     * 生成语法对象, 通过文件地址
     */
    public @NotNull TitleSyntax copyTo() {
        TitleSyntax titleSyntax = new TitleSyntax();
        titleSyntax.setLevel(level);
        titleSyntax.setTitleName(titleName);
        return titleSyntax;
    }
}
