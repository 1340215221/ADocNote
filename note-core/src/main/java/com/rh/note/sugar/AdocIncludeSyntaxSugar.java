package com.rh.note.sugar;

import com.rh.note.constants.AdocFilePathEnum;
import com.rh.note.constants.RegexConstants;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 引用adoc文件快捷语法
 */
@Getter
public class AdocIncludeSyntaxSugar {
    /**
     * 行内容匹配正则
     */
    private final static String regex = "^(\\s*)=>([0-9]+)\\s+(" + RegexConstants.title_name_regex + ")\\s*$";
    /**
     * 缩进
     */
    @NonNull
    private String indent;
    /**
     * 标题级别
     */
    @NonNull
    private int targetLevel;
    /**
     * 标题名
     */
    @NonNull
    private String targetTitleName;

    public @Nullable AdocIncludeSyntaxSugar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        indent = matcher.group(1);
        targetLevel = Integer.parseInt(matcher.group(2));
        targetTitleName = matcher.group(3);
        return this;
    }

    /**
     * 生成引用相对路径
     */
    public @Nullable String generateIncludePath(String currentFilePath) {
        AdocFilePathEnum filePathEnum = AdocFilePathEnum.findByFilePath(currentFilePath);
        if (filePathEnum == null) {
            return null;
        }
        String nextLevelAdocDirectory = filePathEnum.getNextLevelAdocDirectory();
        if (StringUtils.isBlank(nextLevelAdocDirectory)) {
            return null;
        }
        return nextLevelAdocDirectory + targetTitleName + ".adoc";
    }

    /**
     * 指向文件后缀
     */
    public @NotNull String getExtName() {
        return "adoc";
    }
}
