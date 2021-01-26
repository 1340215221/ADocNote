package com.rh.note.syntax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import com.rh.note.constants.RegexConstants;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 引用语法
 */
public class IncludeSyntax {

    /**
     * 正则匹配
     */
    private static final String regex = "^(\\s*)include::(" + RegexConstants.file_path_regex + ")\\[(?:lines=([0-9]+)(?:..([0-9]+))?)?\\]$";
    /**
     * 缩进
     */
    private String indent;
    /**
     * 引用文件路径
     */
    private String includePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String extName;
    /**
     * 引用行号1
     */
    private Integer lineNum1;
    /**
     * 引用行号2
     */
    private Integer lineNum2;

    public @Nullable IncludeSyntax init(String lineContent) {
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        indent = matcher.group(1);
        includePath = matcher.group(2);
        fileName = FileUtil.getName(includePath);
        extName = FileUtil.extName(includePath);
        lineNum1 = handleLineNum(matcher.group(3));
        lineNum2 = handleLineNum(matcher.group(4));
        return this;
    }

    /**
     * 处理行号
     */
    private Integer handleLineNum(String numStr) {
        if (StringUtils.isBlank(numStr) || !numStr.matches(RegexConstants.int_number_regex)) {
            return null;
        }
        return Integer.parseInt(numStr);
    }
}
