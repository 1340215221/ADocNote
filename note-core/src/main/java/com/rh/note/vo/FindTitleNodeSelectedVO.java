package com.rh.note.vo;

import com.rh.note.ao.OpenNewFileByFilePathAO;
import com.rh.note.line.TitleLine;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 获得被选择节点的标题对象
 */
@Getter
public class FindTitleNodeSelectedVO {
    /**
     * 标题行对象
     */
    @Setter
    private String filePath;

    public void copy(TitleLine titleLine) {
        if (titleLine == null || titleLine.getBeanPath() == null || StringUtils.isBlank(titleLine.getBeanPath().getFilePath())) {
            return;
        }
        filePath = titleLine.getBeanPath().getFilePath();
    }

    public @NotNull OpenNewFileByFilePathAO copyTo() {
        OpenNewFileByFilePathAO ao = new OpenNewFileByFilePathAO();
        ao.setFilePath(filePath);
        return ao;
    }
}