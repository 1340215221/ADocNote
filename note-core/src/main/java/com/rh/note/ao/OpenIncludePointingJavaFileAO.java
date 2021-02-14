package com.rh.note.ao;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 打开include指向java文件 参数
 */
public class OpenIncludePointingJavaFileAO extends OpenIncludePointingFileBaseAO {
    @Override
    public boolean checkParamError() {
        return vo == null || StringUtils.isBlank(vo.getTargetFilePath()) || !vo.getTargetFilePath().endsWith("java");
    }

    @Override
    public @Nullable String getTargetFilePath() {
        return vo.getTargetFilePath();
    }

    @Override
    public @NotNull OpenNewFileByFilePathBaseAO copyTo() {
        OpenNewFileByFilePathBaseAO ao = new OpenNewJavaFileByFilePathAO();
        ao.setFilePath(getTargetFilePath());
        return ao;
    }
}
