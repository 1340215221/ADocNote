package com.rh.note.vo;

import com.rh.note.ao.OpenNewFileByFilePathAO;
import com.rh.note.ao.RenameAdocFileAO;
import com.rh.note.ao.UpdateCaretLineAO;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.util.FilePathUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * 请求include文件新名字 结果
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestNewNameOfIncludeOnCaretLineVO {
    /**
     * 文件地址
     */
    @NonNull
    private String currentFilePath;
    /**
     * include指向相对路径
     */
    @NonNull
    private String includePath;
    /**
     * include指向项目路径
     */
    @NonNull
    private String targetFilePath;
    /**
     * 新include行内容
     */
    @NonNull
    private String newLineContent;
    /**
     * 旧文件名
     */
    @NonNull
    private String oldName;
    /**
     * 新文件名
     */
    @NonNull
    private String newName;

    public static @Nullable RequestNewNameOfIncludeOnCaretLineVO getInstance(String currentFilePath, IncludeSyntax syntax, String newName) {
        if (StringUtils.isBlank(currentFilePath) || syntax == null || StringUtils.isBlank(newName)) {
            return null;
        }
        String targetFilePath = FilePathUtil.includePath2ProFilePath(currentFilePath, syntax.getIncludePath());
        if (StringUtils.isBlank(targetFilePath)) {
            return null;
        }
        String newLineContent = handleNewLineContent(syntax, newName);
        if (StringUtils.isBlank(newLineContent)) {
            return null;
        }
        RequestNewNameOfIncludeOnCaretLineVO vo = new RequestNewNameOfIncludeOnCaretLineVO();
        vo.currentFilePath = currentFilePath;
        vo.includePath = syntax.getIncludePath();
        vo.targetFilePath = targetFilePath;
        vo.oldName = syntax.getFileName();
        vo.newName = newName;
        vo.newLineContent = newLineContent;
        return vo;
    }

    /**
     * 处理新的行内容
     */
    private static @Nullable String handleNewLineContent(IncludeSyntax syntax, String newName) {
        if (syntax == null || StringUtils.isBlank(newName)) {
            return null;
        }
        String parentPath = FilePathUtil.getParent(syntax.getIncludePath());
        if (StringUtils.isBlank(parentPath)) {
            return null;
        }
        StringBuilder result = new StringBuilder()
                .append(syntax.getIndent())
                .append("include::")
                .append(parentPath)
                .append(newName);
        if (StringUtils.isNotBlank(syntax.getExtName())) {
            result.append(".").append(syntax.getExtName());
        }
        result.append("[");
        if (syntax.getLineNum1() != null && syntax.getLineNum2() != null) {
            result.append("lines=")
                    .append(syntax.getLineNum1())
                    .append("..")
                    .append(syntax.getLineNum2());
        }
        if (syntax.getLineNum1() != null) {
            result.append("lines=").append(syntax.getLineNum1());
        }
        if (syntax.getLineNum2() != null) {
            result.append("lines=").append(syntax.getLineNum2());
        }
        result.append("]");
        return result.toString();
    }

    public @NotNull List<String> copyToTargetFilePath() {
        return StringUtils.isNotBlank(getTargetFilePath()) ? Collections.singletonList(getTargetFilePath()) : Collections.emptyList();
    }

    public @NotNull RenameAdocFileAO copyToRenameFile() {
        RenameAdocFileAO ao = new RenameAdocFileAO();
        ao.setFilePath(targetFilePath);
        ao.setNewName(newName);
        return ao;
    }

    public @NotNull UpdateCaretLineAO copyToUpdateCaretLine() {
        UpdateCaretLineAO ao = new UpdateCaretLineAO();
        ao.setFilePath(currentFilePath);
        ao.setNewLineContent(newLineContent);
        return ao;
    }

    public @Nullable OpenNewFileByFilePathAO copyToOpenNewFile() {
        if (StringUtils.isBlank(targetFilePath)) {
            return null;
        }
        OpenNewFileByFilePathAO ao = new OpenNewFileByFilePathAO();
        ao.setFilePath(targetFilePath);
        return ao;
    }
}
