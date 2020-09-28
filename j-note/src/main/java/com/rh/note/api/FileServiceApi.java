package com.rh.note.api;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.path.ProBeanPath;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件服务 操作
 */
public class FileServiceApi {
    /**
     * 设置项目路径
     */
    public void setProjectPath(ClickedHistoryProjectListAO ao) {
        if (ao == null || StringUtils.isBlank(ao.getProjectPath())) {
            return;
        }
        new ProBeanPath().setProjectPath(ao.getProjectPath());
    }
}
