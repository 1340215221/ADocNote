package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.path.FileBeanPath;
import com.rh.note.util.CurrentAdocProConfigUtil;
import com.rh.note.vo.GenerateIncludeSyntaxVO;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 打开新adoc文件编辑区,通过文件路径 参数
 */
public class OpenNewFileByFilePathAO implements BaseAO {
    /**
     * 文件项目路径
     */
    @Setter
    private String filePath;

    @Override
    public boolean checkMissRequiredParams() {
        return StringUtils.isBlank(filePath);
    }

    public @Nullable String getFilePath() {
        return filePath;
    }

    /**
     * 获得绝对路径
     */
    public @Nullable String getAbsolutePath() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        String proPath = CurrentAdocProConfigUtil.getProPath();
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        return proPath + filePath;
    }

    /**
     * 获得对象路径
     */
    public @Nullable FileBeanPath getBeanPath() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return FileBeanPath.getInstance(filePath);
    }

    public void copy(GenerateIncludeSyntaxVO vo) {
        if (vo == null) {
            return;
        }
        filePath = vo.getTargetFilePath();
    }
}
