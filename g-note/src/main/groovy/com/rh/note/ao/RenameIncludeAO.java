package com.rh.note.ao;

import com.rh.note.exception.RequestParamsValidException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * include重命名 参数
 */
public class RenameIncludeAO {
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 指向文件路径
     */
    private String targetFilePath;
    /**
     * 新名字
     */
    private String newName;

    /**
     * 参数校验
     */
    public void checkRequiredItems() {
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(newName) || StringUtils.isBlank(targetFilePath)) {
            throw new RequestParamsValidException();
        }
    }

    public @NotNull String getFilePath() {
        return filePath;
    }

    public RenameIncludeAO setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public @NotNull String getNewName() {
        return newName;
    }

    public RenameIncludeAO setNewName(String newName) {
        this.newName = newName;
        return this;
    }

    public @NotNull String getTargetFilePath() {
        return targetFilePath;
    }

    public RenameIncludeAO setTargetFilePath(String targetFilePath) {
        this.targetFilePath = targetFilePath;
        return this;
    }
}
