package com.rh.note.ao;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 打开include指向adoc文件 参数
 */
public class OpenIncludePointingAdocFileAO extends OpenIncludePointingFileBaseAO {
    @Override
    public boolean checkParamError() {
        return vo == null || StringUtils.isBlank(vo.getTargetFilePath()) || !vo.getTargetFilePath().endsWith("adoc");
    }

    @Override
    public @Nullable String getTargetFilePath() {
        return vo.getTargetFilePath();
    }

    @Override
    public @NotNull OpenNewFileByFilePathBaseAO copyTo() {
        OpenNewFileByFilePathBaseAO ao = new OpenNewAdocFileByFilePathAO();
        ao.setFilePath(getTargetFilePath());
        return ao;
    }
}
