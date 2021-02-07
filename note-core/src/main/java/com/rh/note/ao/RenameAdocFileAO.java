package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.util.CurrentAdocProConfigUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 重命名adoc文件 参数
 */
@Setter
public class RenameAdocFileAO implements BaseAO {
    /**
     * 项目路径
     */
    private final String proPath = CurrentAdocProConfigUtil.getProPath();
    /**
     * 文件项目路径
     */
    private String filePath;
    /**
     * 新名字
     */
    @Getter
    private String newName;

    @Override
    public boolean checkMissRequiredParams() {
        return StringUtils.isBlank(filePath) || StringUtils.isBlank(newName);
    }

    /**
     * 获得绝对路径
     */
    public @Nullable String getAbsolutePath() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        return proPath + filePath;
    }

    public @NotNull UpdateRootTitleOfTextPaneAO copyToUpdateRootNode() {
        UpdateRootTitleOfTextPaneAO ao = new UpdateRootTitleOfTextPaneAO();
        ao.setFilePath(filePath);
        ao.setNewTitleName(newName);
        return ao;
    }
}
