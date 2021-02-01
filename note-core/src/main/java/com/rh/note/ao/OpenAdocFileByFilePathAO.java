package com.rh.note.ao;

import com.rh.note.line.TitleLine;
import com.rh.note.path.FileBeanPath;
import com.rh.note.vo.FindIncludePathInSelectedTextPaneVO;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 打开adoc文件,通过标题节点 参数
 */
public class OpenAdocFileByFilePathAO {

    /**
     * 文件项目路径
     */
    @Getter
    private String filePath;

    public void copy(TitleLine titleLine) {
        if (titleLine == null || titleLine.getBeanPath() == null || StringUtils.isBlank(titleLine.getBeanPath().getFilePath())) {
            return;
        }
        filePath = titleLine.getBeanPath().getFilePath();
    }

    public void copy(FindIncludePathInSelectedTextPaneVO vo) {
        if (vo == null || StringUtils.isBlank(vo.getFilePath())) {
            return;
        }
        filePath = vo.getFilePath();
    }

    public @Nullable FileBeanPath getBeanPath() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return new FileBeanPath().setFilePath(filePath);
    }
}
