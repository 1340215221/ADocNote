package com.rh.note.syntax;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
    private final static String regex = "";
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
        level = matcher.group(2).length();
        titleName = matcher.group(3);
        return this;
    }

    /**
     * 生成includeLine
     */
    public IncludeLine copyTo() {
        IncludeLine includeLine = new IncludeLine();
        includeLine.setIndented(indented);
//        includeLine.setTargetRelativePath();
        includeLine.setTargetFileName(titleName);
        includeLine.setTargetFileSuf("adoc");
        return includeLine;
    }

}
