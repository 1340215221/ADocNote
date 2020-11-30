package com.rh.note.syntax;

import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.constants.BaseConstants;
import com.rh.note.path.TitleBeanPath;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 引用语法
 */
@Data
@Accessors(chain = true)
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
        if (!"adoc".equals(targetFileSuf)) {
            return null;
        }
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
    public @NotNull String getTargetFilePath() {
        return AdocFileTypeEnum.matchByRelativePath(targetRelativePath).getFileDirectory() + targetFileName + "." + targetFileSuf;
    }

    /**
     * 生成语法语句
     */
    public String toString() {
        StringBuilder str = new StringBuilder()
                .append(indented)
                .append("include::")
                .append(targetRelativePath)
                .append("[");
        if (lineStart != null && lineEnd != null) {
            str.append(lineStart)
                    .append("..")
                    .append(lineEnd);
        } else if (lineStart != null) {
            str.append(lineStart);
        } else if (lineEnd != null){
            str.append(lineEnd);
        }
        str.append("]");
        return str.toString();
    }

    /**
     * 复制
     */
    public void copy(IncludeSyntaxSugar sugar) {
        if (sugar == null) {
            return;
        }

        indented = sugar.getIndented();
        sugar.getLevel();
        sugar.getTitleName();
    }

    /**
     * 获得指向文件根标题的对象路径
     */
    public @NotNull TitleBeanPath getBeanPathOfTargetFileRootTitle() {
        return TitleBeanPath.getInstance(getTargetFilePath(), getTargetFileName());
    }

    /**
     * 获得文件名起始偏移量
     */
    public int getStartOffsetOfTargetFileName() {
        return new StringBuilder()
                .append(indented)
                .append("include::")
                .append(targetRelativePath)
                .lastIndexOf(targetFileName);
    }

    /**
     * 获得文件名长度
     */
    public int getLengthOfOfTargetFileName() {
        return targetFileName.length();
    }

    /**
     * 是否为父目录
     */
    public boolean isChildPathOf(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        AdocFileTypeEnum fileType = AdocFileTypeEnum.matchByFilePath(filePath);
        if (fileType == null) {
            return false;
        }

        return fileType.isParentPathOf(getTargetFilePath());
    }
}
