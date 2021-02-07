package com.rh.note.syntax;

import cn.hutool.core.io.FileUtil;
import com.rh.note.constants.AdocFilePathEnum;
import com.rh.note.constants.RegexConstants;
import com.rh.note.sugar.AdocIncludeSyntaxSugar;
import com.rh.note.util.FilePathUtil;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 引用语法
 */
@Getter
public class IncludeSyntax {

    /**
     * 正则匹配
     */
    private static final String regex = "^(\\s*)include::(" + RegexConstants.file_path_regex + ")\\[(?:lines=([0-9]+)(?:..([0-9]+))?)?\\]$";
    /**
     * 缩进
     */
    @NonNull
    private String indent;
    /**
     * 引用文件路径
     */
    @NonNull
    private String includePath;
    /**
     * 文件名
     */
    @NonNull
    private String fileName;
    /**
     * 文件后缀
     */
    @Getter(onMethod = @__({@Nullable}))
    private String extName;
    /**
     * 引用行号1
     */
    @Getter(onMethod = @__({@Nullable}))
    private Integer lineNum1;
    /**
     * 引用行号2
     */
    @Getter(onMethod = @__({@Nullable}))
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

    public @Nullable IncludeSyntax init(AdocIncludeSyntaxSugar syntaxSugar, String currentFilePath) {
        if (syntaxSugar == null || StringUtils.isBlank(currentFilePath)) {
            return null;
        }
        indent = syntaxSugar.getIndent();
        includePath = syntaxSugar.generateIncludePath(currentFilePath);
        fileName = syntaxSugar.getTargetTitleName();
        extName = syntaxSugar.getExtName();
        return StringUtils.isNotBlank(includePath) ? this : null;
    }

    @Override
    public @NotNull String toString() {
        StringBuilder result = new StringBuilder()
                .append(indent)
                .append("include::")
                .append(includePath)
                .append("[");
        if (lineNum1 != null && lineNum2 != null) {
            result.append("lines=")
                    .append(lineNum1)
                    .append("..")
                    .append(lineNum2);
        }
        if (lineNum1 != null) {
            result.append("lines=").append(lineNum1);
        }
        if (lineNum2 != null) {
            result.append("lines=").append(lineNum2);
        }
        result.append("]");
        return result.toString();
    }

    /**
     * 指向文件是否为adoc文件
     */
    public boolean isAdocFile() {
        return "adoc".equalsIgnoreCase(extName);
    }

    /**
     * 在adoc项目结构中
     */
    public boolean isInProStructure(String filePath, String proPath) {
        // 引用路径为绝对路径
        if (FileUtil.isAbsolutePath(includePath)) {
            return StringUtils.isNotBlank(proPath) && includePath.startsWith(proPath);
        }
        // 引用路径为相对路径
        String targetFilePath = FilePathUtil.includePath2ProFilePath(filePath, includePath);
        return AdocFilePathEnum.isInProStructure(targetFilePath);
    }
}
