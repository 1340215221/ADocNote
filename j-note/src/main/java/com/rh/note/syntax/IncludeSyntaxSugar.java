package com.rh.note.syntax;

import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.constants.BaseConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * include语法糖
 */
@Data
public class IncludeSyntaxSugar {

    /**
     * 匹配正则
     * todo
     */
    private final static String regex = "^(\\s*)=>([1-9]) ([" + BaseConstants.file_name_regex + "]+?)\\s*$";
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

    /**
     * 通过行内容初始化
     */
    public IncludeSyntaxSugar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }

        indented = matcher.group(1);
        level = Optional.ofNullable(matcher.group(2))
                .filter(str -> str.matches("[0-9]+"))
                .map(Integer::valueOf)
                .orElse(null);
        titleName = matcher.group(3);
        return this;
    }

    /**
     * 生成include语法对象
     */
    public @NotNull IncludeSyntax copyToByFilePath(String filePath) {
        IncludeSyntax includeSyntax = new IncludeSyntax();
        includeSyntax.setIndented(indented);
        includeSyntax.setTargetFileName(titleName);
        includeSyntax.setTargetFileSuf("adoc");
        String targetRelativePath = AdocFileTypeEnum.matchByFilePath(filePath).getRelativePathOfNextDirectory() + titleName + ".adoc";
        includeSyntax.setTargetRelativePath(targetRelativePath);
        return includeSyntax;
    }

}
