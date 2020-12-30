package com.rh.note.syntax;

import com.rh.note.constants.BaseConstants;
import com.rh.note.path.ProBeanPath;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * include java 语法
 */
@Data
@Accessors(chain = true)
public class IncludeJavaSyntax {
    /**
     * include::../../maven/src/main/java/com/rh/note/Main.java[]
     */
    private static final String regex = "^(\\s*)include::(" + BaseConstants.file_path_regex + ").java\\[(?:lines=([0-9]+)(?:\\.\\.([0-9]+))?)?\\]\\s*$";
    /**
     * 缩进
     */
    private String indented;
    /**
     * java文件相对路径
     */
    private String targetRelativePath;
    /**
     * 文件名
     */
    private String targetFileName;
    /**
     * 后缀
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

    public @Nullable IncludeJavaSyntax init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        indented = matcher.group(1);
        targetRelativePath = matcher.group(2) + ".java";
        targetFileName = parsingTargetFileName();
        targetFileSuf = "java";
        lineStart = parsingNumber(matcher.group(3));
        lineEnd = parsingNumber(matcher.group(4));
        return this;
    }

    /**
     * 解析指向java文件名
     */
    private String parsingTargetFileName() {
        if (StringUtils.isBlank(targetRelativePath)) {
            return null;
        }
        int startIndex = targetRelativePath.lastIndexOf('/');
        int endIndex = targetRelativePath.lastIndexOf(".java");
        return targetRelativePath.substring(startIndex + 1, endIndex);
    }

    /**
     * todo 从 includeSyntax复制过来的
     * 解析数字
     */
    private Integer parsingNumber(String str) {
        if (StringUtils.isBlank(str) || !str.matches("[0-9]+")) {
            return null;
        }
        return Integer.valueOf(str);
    }

    /**
     * todo
     * 获得指向绝对路径
     */
    public @Nullable String getTargetAbsolutePath() {
        if (StringUtils.isBlank(targetRelativePath)) {
            return null;
        }
        String temp = targetRelativePath;
        if (targetRelativePath.startsWith("../../")) {
            temp = targetRelativePath.substring(6);
        }
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return null;
        }
        return projectPath + temp;
    }

    /**
     * 更新起始行号
     */
    public void updateStartLine(int lineNumber) {
        lineStart = lineNumber;
    }

    /**
     * 更新起始行号
     */
    public void updateEndLine(int lineNumber) {
        lineEnd = lineNumber;
    }

    /**
     * 转为include语句
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(indented)
                .append("include::")
                .append(targetRelativePath)
                .append("[");
        if (lineStart != null || lineEnd != null) {
            result.append("lines=");
        }
        if (lineStart != null && lineEnd != null) {
            result.append(lineStart)
                    .append("..")
                    .append(lineEnd);
        } else if (lineStart != null) {
            result.append(lineStart);
        } else if (lineEnd != null) {
            result.append(lineEnd);
        }
        result.append("]");
        return result.toString();
    }
}
