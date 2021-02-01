package com.rh.note.vo;

import com.rh.note.constants.FilePathEnum;
import com.rh.note.syntax.IncludeSyntax;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 查询include路径, 通过被选择的编辑区 结果
 */
@NoArgsConstructor
public class FindIncludePathInSelectedTextPaneVO {
    /**
     * include路径
     */
    private String includePath;
    /**
     * include所属文件路径
     */
    @Setter
    private String currentFilePath;

    public void copy(IncludeSyntax includeSyntax) {
        if (includeSyntax == null || StringUtils.isBlank(includeSyntax.getIncludePath())) {
            return;
        }
        includePath = includeSyntax.getIncludePath();
    }

    /**
     * 获得include指向文件的项目路径
     */
    public @Nullable String getFilePath() {
        return FilePathEnum.includePath2ProPath(currentFilePath, includePath);
    }
}
