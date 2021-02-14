package com.rh.note.ao;

import com.rh.note.vo.FindIncludePathInSelectedTextPaneVO;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 打开include指向文件 参数
 */
@Setter
public abstract class OpenIncludePointingFileBaseAO {

    protected FindIncludePathInSelectedTextPaneVO vo;

    /**
     * 检查参数
     */
    public abstract boolean checkParamError();

    public abstract @Nullable String getTargetFilePath();

    public abstract @NotNull OpenNewFileByFilePathBaseAO copyTo();
}
