package com.rh.note.vo;

import com.rh.note.common.BaseFileConfig;
import com.rh.note.constants.AdocFilePathEnum;
import com.rh.note.syntax.IncludeSyntax;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * include语法 结果
 */
@RequiredArgsConstructor
public class GenerateIncludeSyntaxVO extends BaseFileConfig {
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
     * 获得绝对路径
     */
    public @Nullable String getTargetAbsolutePath() {
        String proPath = getProPath();
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
        return AdocFilePathEnum.includePath2ProPath(filePath, syntax.getIncludePath());
    }
}
