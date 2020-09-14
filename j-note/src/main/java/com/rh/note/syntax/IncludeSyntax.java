package com.rh.note.syntax;

import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.constants.BaseConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 引用语法
 */
@Data
public class IncludeSyntax {

    /**
     * 匹配正则
     */
    private final static String regex = "^(\\s*)include::(" + BaseConstants.file_path_regex + ")\\.([0-9a-zA-Z]+)\\[(?:([0-9]+)\\.\\.([0-9]+))?\\]\\s*$";
    /**
     * 缩进
     */
    private String indented;
    /**
     * 指向相对路径
     */
    private String targetRelativePath;
    /**
     * 指向文件名
     */
    private String targetFileName;
    /**
     * 文件后缀
     */
    private String targetFileSuf;
    /**
     * 引用行,起始
     */
    private Integer lineStart;
    /**
     * 引用行,结束
     */
    private Integer lineEnd;

    /**
     * 通过行内容初始化
     */
    public IncludeSyntax init(String lienContent) {
        if (StringUtils.isBlank(lienContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lienContent);
        if (!matcher.find()) {
            return null;
        }
        indented = matcher.group(1);
        targetRelativePath = matcher.group(2) + ".adoc";
        targetFileName = parsingTargetFileName(matcher.group(2));
        targetFileSuf = matcher.group(3);
        lineStart = parsingNumber(matcher.group(4));
        lineEnd = parsingNumber(matcher.group(5));
        return this;
    }

    /**
     * 解析数字
     */
    private Integer parsingNumber(String str) {
        if (StringUtils.isBlank(str) || !str.matches("[0-9]+")) {
            return null;
        }
        return Integer.valueOf(str);
    }

    /**
     * 解析指向相对路径
     */
    private String parsingTargetFileName(String group_2) {
        if (StringUtils.isBlank(group_2)) {
            return null;
        }
        int index = group_2.lastIndexOf("/");
        return group_2.substring(index + 1);
    }

    /**
     * 获得指向文件路径
     */
    public String getTargetFilePath() {
        return AdocFileTypeEnum.matchByRelativePath(targetRelativePath).getFileDirectory() + targetFileName + "." + targetFileSuf;
    }
}
