package com.rh.note.vo;

import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.util.CurrentAdocProConfigUtil;
import com.rh.note.util.FilePathUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * include语法 结果
 */
@RequiredArgsConstructor
public class GenerateIncludeSyntaxVO {
    /**
     * include语法信息
     */
    @NonNull
    private IncludeSyntax syntax;
    /**
     * include语句所属文件的项目路径
     */
    @NonNull
    @Getter
    private String filePath;
    /**
     * 指向文件的根标题级别
     */
    @NonNull
    private int targetRootTitleLevel;

    /**
     * 获得绝对路径
     */
    public @Nullable String getTargetAbsolutePath() {
        String proPath = CurrentAdocProConfigUtil.getProPath();
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        String targetFilePath = getTargetFilePath();
        if (StringUtils.isBlank(targetFilePath)) {
            return null;
        }
        return proPath + targetFilePath;
    }

    /**
     * 获得include指向文件的项目路径
     */
    public @Nullable String getTargetFilePath() {
        return FilePathUtil.includePath2ProFilePath(filePath, syntax.getIncludePath());
    }

    /**
     * include生成的adoc文件的初始化内容
     */
    public @NotNull String getInitAdocFileContent() {
        StringBuilder result = new StringBuilder()
                .append("\n\n");
        for (int i = 0; i < targetRootTitleLevel; i++) {
            result.append("=");
        }
        result.append(" ")
                .append(syntax.getFileName())
                .append("\n\n");
        return result.toString();
    }
}
