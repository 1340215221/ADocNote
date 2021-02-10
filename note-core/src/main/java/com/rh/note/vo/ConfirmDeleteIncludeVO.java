package com.rh.note.vo;

import com.rh.note.ao.DeleteAdocFileAO;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.util.CurrentAdocProConfigUtil;
import com.rh.note.util.FilePathUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * 确认删除include 结果
 */
public class ConfirmDeleteIncludeVO {

    /**
     * 指向文件相对路径
     */
    private String includePath;
    /**
     * 当前文件项目路径
     */
    @Setter
    @Getter
    private String filePath;

    public void copy(IncludeSyntax syntax) {
        if (syntax == null) {
            return;
        }
        includePath = syntax.getIncludePath();
    }

    public @NotNull List<String> copyToTargetFilePath() {
        String targetFilePath = FilePathUtil.includePath2ProFilePath(filePath, includePath);
        return StringUtils.isNotBlank(targetFilePath) ? Collections.singletonList(targetFilePath) : Collections.emptyList();
    }

    public @Nullable DeleteAdocFileAO copyToDeleteAdocFile() {
        String proPath = CurrentAdocProConfigUtil.getProPath();
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        String targetFilePath = FilePathUtil.includePath2ProFilePath(filePath, includePath);
        if (StringUtils.isBlank(targetFilePath)) {
            return null;
        }
        DeleteAdocFileAO ao = new DeleteAdocFileAO();
        ao.setAbsolutePath(proPath + targetFilePath);
        return ao;
    }
}
