package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.exception.RequestParamsValidException;
import com.rh.note.path.FileBeanPath;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

/**
 * 打开adoc文件,通过标题节点 参数
 */
@Data
public class OpenAdocFileByTitleNodeAO implements BaseAO {

    /**
     * 文件项目路径
     */
    private FileBeanPath beanPath;

    public @Nullable String getFilePath() {
        return beanPath != null ? beanPath.getFilePath() : null;
    }

    @Override
    public void checkRequiredItems() throws RequestParamsValidException {
    }
}
