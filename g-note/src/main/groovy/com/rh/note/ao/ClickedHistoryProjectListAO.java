package com.rh.note.ao;

import com.rh.note.base.ICheckRequiredItems;
import com.rh.note.exception.RequestParamsValidException;
import org.apache.commons.lang3.StringUtils;

/**
 * 点击历史项目参数
 */
public class ClickedHistoryProjectListAO implements ICheckRequiredItems {

    /**
     * 项目文件路径
     */
    private String projectPath;

    public String getProjectPath() {
        return projectPath;
    }

    public ClickedHistoryProjectListAO setProjectPath(String projectPath) {
        this.projectPath = projectPath;
        return this;
    }

    /**
     * 参数检查
     */
    @Override
    public void checkRequiredItems() {
        if (StringUtils.isBlank(projectPath)) {
            throw new RequestParamsValidException();
        }
    }
}
